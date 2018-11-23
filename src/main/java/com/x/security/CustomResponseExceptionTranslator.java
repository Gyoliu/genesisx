package com.x.security;

import com.x.dto.ResultDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * @Author: liuxing
 * @Date: 2018/11/23 10:08
 * @Description:
 */
public class CustomResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
        OAuth2Exception ase = (OAuth2Exception)this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if(ase != null) {
            return this.handleOAuth2Exception((OAuth2Exception)ase);
        } else {
            AuthenticationException ase1 = (AuthenticationException)this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
            if(ase1 != null) {
                return this.handleOAuth2Exception(new CustomResponseExceptionTranslator.UnauthorizedException(e.getMessage(), e));
            } else {
                AccessDeniedException ase2 = (AccessDeniedException)this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
                if(ase2 instanceof AccessDeniedException) {
                    return this.handleOAuth2Exception(new CustomResponseExceptionTranslator.ForbiddenException(ase2.getMessage(), ase2));
                } else {
                    HttpRequestMethodNotSupportedException ase3 = (HttpRequestMethodNotSupportedException)this.throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
                    return ase3 instanceof HttpRequestMethodNotSupportedException?this.handleOAuth2Exception(new CustomResponseExceptionTranslator.MethodNotAllowed(ase3.getMessage(), ase3)):this.handleOAuth2Exception(new CustomResponseExceptionTranslator.ServerErrorException(e.getMessage(), e));
                }
            }
        }
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if(status == HttpStatus.UNAUTHORIZED.value() || e instanceof InsufficientScopeException) {
            headers.set("WWW-Authenticate", String.format("%s %s", new Object[]{"Bearer", e.getSummary()}));
        }
        ResponseEntity response = null;
        switch (status){
            case 401:
                response = new ResponseEntity(new ResultDto<>(HttpStatus.UNAUTHORIZED, "认证失败！"), headers, HttpStatus.valueOf(status));
                break;
            case 403:
                response = new ResponseEntity(new ResultDto<>(HttpStatus.FORBIDDEN, "拒绝访问！"), headers, HttpStatus.valueOf(status));
                break;
            case 405:
                response = new ResponseEntity(new ResultDto<>(HttpStatus.METHOD_NOT_ALLOWED), headers, HttpStatus.valueOf(status));
                break;
            case 500:
                response = new ResponseEntity(new ResultDto<>(HttpStatus.INTERNAL_SERVER_ERROR, "服务器异常！"), headers, HttpStatus.valueOf(status));
                break;
                default:
                    response = new ResponseEntity(e, headers, HttpStatus.valueOf(status));
                    break;
        }
        return response;
    }

    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
        this.throwableAnalyzer = throwableAnalyzer;
    }

    private static class MethodNotAllowed extends OAuth2Exception {
        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "method_not_allowed";
        }

        @Override
        public int getHttpErrorCode() {
            return 405;
        }
    }

    private static class UnauthorizedException extends OAuth2Exception {
        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "unauthorized";
        }

        @Override
        public int getHttpErrorCode() {
            return 401;
        }
    }

    private static class ServerErrorException extends OAuth2Exception {
        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "server_error";
        }

        @Override
        public int getHttpErrorCode() {
            return 500;
        }
    }

    private static class ForbiddenException extends OAuth2Exception {
        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "access_denied";
        }

        @Override
        public int getHttpErrorCode() {
            return 403;
        }
    }
}