package com.genesis.x.security;

import com.alibaba.fastjson.JSON;
import com.genesis.x.config.SpringContextHolder;
import com.genesis.x.dao.entity.SystemLog;
import com.genesis.x.service.ISystemLogService;
import com.genesis.x.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            String userId = httpServletRequest.getParameter("userId");
            systemLog.setCreator(Integer.parseInt(userId));
        }
        bean.insert(systemLog);

        // 移除token
        WebUtils.removeCurrentToken();
    }
}