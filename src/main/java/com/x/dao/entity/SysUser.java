package com.x.dao.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUser {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private Boolean locking;

    private Date createDate;

    private Integer createrId;

    private Date modifyDate;

    private Integer modifierId;

    private Integer roleId;

    private SysUserInfo createUser;
    private SysUserInfo modifyUser;
    private SysUserInfo userInfo;

    private SysRole roles;

    private List<SysResource> resources;

}