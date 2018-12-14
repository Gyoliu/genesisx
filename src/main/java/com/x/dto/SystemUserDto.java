package com.x.dto;

import com.x.dao.entity.SysResource;
import com.x.dao.entity.SysRole;
import com.x.dao.entity.SysUser;
import com.x.dao.entity.SysUserInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Collection;
import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/21 9:28
 * @Description:
 */
@Data
public class SystemUserDto {

    public SystemUserDto(){
    }
    public SystemUserDto(SysUser sysUser){
        BeanUtils.copyProperties(sysUser, this);
    }
    public SystemUserDto(SysUser sysUser, OAuth2AccessToken oAuth2AccessToken){
        this(sysUser);
        this.oAuth2AccessToken = oAuth2AccessToken;
    }

    private Integer id;

    private String username;

    private Boolean locking;

    private Boolean enable;

    private SysUserInfo userInfo;

    private SysRole sysRole;

    private List<SysResource> sysResources;

    private OAuth2AccessToken oAuth2AccessToken;

    private Collection<? extends GrantedAuthority> authorities;

}