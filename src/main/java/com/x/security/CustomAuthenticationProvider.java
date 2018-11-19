package com.x.security;

import com.x.dao.entity.SysUser;
import com.x.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * @Author: liuxing
 * @Date: 2018/11/8 15:44
 * @Description:
 */

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("==================authenticate name:{}===========",authentication.getName());
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        SysUser sysUser = sysUserService.selectByUsername(username);
        if(sysUser == null){
            log.error("username not found exception:{}", username);
            throw new UsernameNotFoundException("username or password error!");
        }
        if (!password.equals(sysUser.getPassword())) {
            log.error("{} - wrong password exception！", username);
            throw new AuthenticationCredentialsNotFoundException("username or password error!");
        }
        if(!sysUser.getEnable()){
            log.error("{} - is disabled！", username);
            throw new DisabledException("account is disabled!");
        }
        if(sysUser.getLocking()){
            log.error("{} - is locked！", username);
            throw new LockedException("account is locked!");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, authentication.getAuthorities());
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
