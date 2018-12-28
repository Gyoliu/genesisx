package com.genesis.x.security;

import com.alibaba.fastjson.JSON;
import com.genesis.x.config.SpringContextHolder;
import com.genesis.x.service.ISystemLogService;
import com.genesis.x.dao.entity.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

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
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public AuthenticationSuccessHandler(String homeUrl){
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(homeUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("-------------------onAuthenticationSuccess------------------------");
        log.info(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication()));
        ISystemLogService bean = SpringContextHolder.getBean(ISystemLogService.class);
        SystemLog systemLog = new SystemLog();
        systemLog.setType(SystemLog.Type.LOGIN.name());
        systemLog.setAfterData(JSON.toJSONString(authentication));
        bean.insert(systemLog);
        super.onAuthenticationSuccess(request, response, authentication);
        log.info("-------------------onAuthenticationSuccess end------------------------");
    }
}