package com.x.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuxing
 * @Date: 2018/11/19 19:50
 * @Description:
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String authenticateKey = "authenticate";

    private ClientDetailsService clientDetailsService;
    private TokenGranter tokenGranter;
    private String clientId;
    private String clientSecret;

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager
            , ClientDetailsService clientDetailsService
            , TokenGranter tokenGranter
            , String clientId, String clientSecret){
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationFailureHandler(new AuthenticationFailureHandler());
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
        this.clientDetailsService = clientDetailsService;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenGranter = tokenGranter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = "";
            String password = "";
            try {
                String requestBody = IOUtils.toString(request.getInputStream());
                JSONObject jsonObject = (JSONObject)JSONObject.parse(requestBody);
                if(jsonObject == null){jsonObject = new JSONObject();}
                username = jsonObject.getString(this.getUsernameParameter());
                if(StringUtils.isEmpty(username)){
                    username = this.obtainUsername(request);
                }
                password = jsonObject.getString(this.getPasswordParameter());
                if(StringUtils.isEmpty(password)){
                    password = this.obtainPassword(request);
                }
            } catch (IOException e) {
                log.error("login request.getInputStream() error!{}", e.getMessage());
            } catch (Exception ex){
                log.error("login process error!{}", ex.getMessage());
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);

            /*if(!authRequest.isAuthenticated() || !authenticate.isAuthenticated()){
                authenticate.setAuthenticated(false);
                return authenticate;
            }*/

            ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
            DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("client_id", clientId);
            parameters.put("client_secret", clientSecret);
            parameters.put("username", username);
            parameters.put("password", password);
            parameters.put("grant_type", "password");
            parameters.put("scope", request.getParameter("scope"));
            TokenRequest tokenRequest = defaultOAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);
            OAuth2AccessToken token = this.tokenGranter.grant(tokenRequest.getGrantType(), tokenRequest);
            log.info("----username:{}, token:{}", username, JSON.toJSONString(token));
            DefaultOAuth2AccessToken token1 = (DefaultOAuth2AccessToken) token;
            Map<String, Object> additionalInformation = new HashMap<>();
            additionalInformation.put("authenticate", authenticate);
            token1.setAdditionalInformation(additionalInformation);

            return authenticate;
        }
    }
}