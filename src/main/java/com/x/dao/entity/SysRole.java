package com.x.dao.entity;

import lombok.Data;

@Data
public class SysRole {
    private Integer id;

    private String role;

    public Integer getId() {
        return id;
    }
}