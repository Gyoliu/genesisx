package com.x.security;

import com.alibaba.fastjson.JSON;
import com.x.dto.ResultDto;
import com.x.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuxing
 * @Date: 2018/11/19 10:27
 * @Description:
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if(HttpUtils.isAjaxRequest(request)){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().write(JSON.toJSONString(new ResultDto<>(HttpStatus.UNAUTHORIZED, "认证失败！")).getBytes());

        } else {
            response.sendRedirect("/login");
        }
    }
}