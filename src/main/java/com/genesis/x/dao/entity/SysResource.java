package com.genesis.x.dao.entity;

import lombok.Data;

@Data
public class SysResource {
    private Integer id;

    private String name;

    private String path;

    private String type;

    private Integer parentId;

    private Integer level;

    private String permission;

    private Integer status;

    private String icon;

    private Integer roleId;

}