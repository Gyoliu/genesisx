package com.genesis.x.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * liuxing
 * @param <T>
 */
@Data
public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = 7934727220113660197L;

    public static final int SuccessCode = 200;
    public static final String SuccessMessage = "操作成功！";

    private static final String PATTERN = "yyyy-MM-dd HH:mm:sss";

    private Integer code;

    private String message;

    private T data;

    private String timestamp;

    public static ResultDto success(){
        return new ResultDto(SuccessCode, SuccessMessage);
    }

    public ResultDto(){}

    public ResultDto(EnumError enumError){
        this.code = enumError.getCode();
        this.message = enumError.getName();
        this.timestamp = DateFormatUtils.format(new Date(), PATTERN);
    }

    public ResultDto(HttpStatus httpStatus, String message){
        this.code = httpStatus.value();
        if(StringUtils.isNotEmpty(message)){
            this.message = message;
        } else {
            this.message = httpStatus.getReasonPhrase();
        }
        this.timestamp = DateFormatUtils.format(new Date(), PATTERN);
    }

    public ResultDto(HttpStatus httpStatus){
        this(httpStatus, null);
    }

    public ResultDto(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = DateFormatUtils.format(new Date(), PATTERN);
    }

    public ResultDto(T data) {
        this.code = SuccessCode;
        this.message = SuccessMessage;
        this.data = data;
        this.timestamp = DateFormatUtils.format(new Date(), PATTERN);
    }

}
