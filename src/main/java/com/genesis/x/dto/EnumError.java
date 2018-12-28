package com.genesis.x.dto;

import lombok.Data;

/**
 *
 */
public enum EnumError {

    SYSTEM_ERROR(10000, "系统异常"),
    PARAMS_ERROR(10001, "参数错误"),
    UNAUTHORIZED_ERROR(10003, "UNAUTHORIZED"),
    NOT_EXIST(10004, "资源不存在"),

    USER_NOT_EXIST(20000, "用户不存在")
    ;

    private Integer code;

    private String name;

    EnumError(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
