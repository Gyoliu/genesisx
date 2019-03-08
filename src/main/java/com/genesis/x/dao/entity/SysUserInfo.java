package com.genesis.x.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserInfo {
    private Integer id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private Date birthDate;

    private String personalSignature;

    private String remarks;

    private Date createDate;

    private Integer creatorId;

    private Date modifyDate;

    private Integer modifierId;

    private byte[] headPortrait;

}