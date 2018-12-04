package com.x.security;

import com.alibaba.fastjson.JSON;
import com.x.config.SpringContextHolder;
import com.x.dao.entity.SystemLog;
import com.x.service.ISystemLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liuxing
 * @Date: 2018/12/4 10:48
 * @Description:
 */
public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        ISystemLogService bean = SpringContextHolder.getBean(ISystemLogService.class);
        SystemLog systemLog = new SystemLog();
        systemLog.setType(SystemLog.Type.LOGOUT.name());
        systemLog.setBeforeData(JSON.toJSONString(authentication));
        bean.insert(systemLog);
    }
}