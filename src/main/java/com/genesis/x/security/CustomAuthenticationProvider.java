package com.genesis.x.security;

import com.genesis.x.dao.entity.SysUser;
import com.genesis.x.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * @Author: liuxing
 * @Date: 2018/11/8 15:44
 * @Description: DaoAuthenticationProvider
 */

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("==================authenticate name:{}===========",authentication.getName());
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new BadCredentialsException("username or password error!");
        }
        SysUser sysUser = sysUserService.selectByUsername(username);
        if(sysUser == null){
            log.error("username not found exception:{}", username);
            throw new UsernameNotFoundException("username or password error!");
        }
        if (!passwordEncoder.matches(password, sysUser.getPassword())) {
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
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, CustomUserDetailsManager.getGrantedAuthorities(sysUser));
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
