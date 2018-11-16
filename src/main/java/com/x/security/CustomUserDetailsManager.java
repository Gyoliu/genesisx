package com.x.security;

import com.alibaba.fastjson.JSON;
import com.x.dao.entity.SysUser;
import com.x.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author: liuxing
 * @Date: 2018/11/8 14:52
 * @Description:
 */

@Slf4j
@Component
public class CustomUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectByUsername(username);
        if(sysUser == null){
            log.error("-----------user:{} not found-------------",username);
            throw new UsernameNotFoundException("user not found!");
        }
        User user = new User(sysUser.getUsername(), sysUser.getPassword()
                , sysUser.getLocking(), true, true, true
                , CustomUserDetailsManager.getGrantedAuthorities(sysUser));
        return user;
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        SysUser sysUser = sysUserService.selectByUsername(userDetails.getUsername());
        if(sysUser == null){
            return null;
        }
        this.changePassword(sysUser.getPassword(), newPassword);
        User user = new User(sysUser.getUsername(), newPassword
                , sysUser.getLocking(), true, true, true
                , getGrantedAuthorities(sysUser));
        return user;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        System.out.println(JSON.toJSONString(userDetails));
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        System.out.println(JSON.toJSONString(userDetails));
    }

    @Override
    public void deleteUser(String username) {
        SysUser sysUser = sysUserService.selectByUsername(username);
        if(sysUser != null){
            SysUser sysUserParam = new SysUser();
            sysUserParam.setId(sysUser.getId());
            sysUserParam.setLocking(true);
            sysUserParam.setModifierId(sysUser.getId());
            sysUserParam.setModifyDate(new Date());
            sysUserService.updateByPrimaryKeySelective(sysUserParam);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser == null) {
            throw new AccessDeniedException("Can\'t change password as no Authentication object found in context for current user.");
        } else {
            String username = currentUser.getName();
            log.debug("Changing password for user \'" + username + "\'");
            if(this.authenticationManager != null) {
                log.debug("Reauthenticating user \'" + username + "\' for password change request.");
                this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
            } else {
                log.debug("No authentication manager set. Password won\'t be re-checked.");
            }
            SysUser sysUser = sysUserService.selectByUsername(username);
            if(sysUser == null) {
                throw new IllegalStateException("Current user doesn\'t exist in database.");
            } else {
                sysUser.setUsername(username);
                sysUser.setPassword(newPassword);
                sysUser.setModifierId(sysUser.getId());
                sysUser.setModifyDate(new Date());
                sysUserService.updatePasswordByUsername(sysUser);
            }
        }
    }

    @Override
    public boolean userExists(String username) {
        SysUser sysUser = sysUserService.selectByUsername(username);
        if(ObjectUtils.isEmpty(sysUser)){
            return false;
        } else {
            return true;
        }
    }

    public static List<GrantedAuthority> getGrantedAuthorities(SysUser user){
        List<GrantedAuthority> authorities = new ArrayList<>();
        //todo 这里应该改成一个用户对应多个角色
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoles().getRole()));
        return authorities;
    }

}
