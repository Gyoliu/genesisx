package com.x.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * liuxing
 * @param <T>
 */
@Data
public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = 7934727220113660197L;

    public static final int SuccessCode = 200;
    public static final String SuccessMessage = "操作成功！";

    private Integer code;

    private String message;

    private T data;

    public ResultDto(){}

    public ResultDto(EnumError enumError){
        this.code = enumError.getCode();
        this.message = enumError.getName();
    }

    public ResultDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultDto(T data) {
        this.code = SuccessCode;
        this.message = SuccessMessage;
        this.data = data;
    }

}
