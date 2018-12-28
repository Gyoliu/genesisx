package com.genesis.x.security;

import com.alibaba.fastjson.JSON;
import com.genesis.x.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuxing
 * @Date: 2018/11/23 15:08
 * @Description:
 */
@Slf4j
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private String targetUrl;

    public CustomLogoutSuccessHandler(String targetUrl){
        this.targetUrl = targetUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("==============logout========================");
        /*super.setDefaultTargetUrl(this.targetUrl);
        super.onLogoutSuccess(request, response, authentication);*/
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.getOutputStream().write(JSON.toJSONString(new ResultDto<>(HttpStatus.OK, "操作成功！")).getBytes());
    }
}