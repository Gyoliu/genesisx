package com.genesis.x.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUser {

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    private Boolean locking;

    private Boolean enable;

    private Date createDate;

    private Integer creatorId;

    private Date modifyDate;

    private Integer modifierId;

    private Integer roleId;

    private Integer userInfoId;

    private SysUserInfo createUser;
    private SysUserInfo modifyUser;
    private SysUserInfo userInfo;

    private SysRole sysRole;

    private List<SysResource> sysResources;

}