package com.genesis.x.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxing
 * @param <T>
 */
@Data
public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = 7934727220113660197L;

    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MESSAGE = "操作成功！";
    public static final ResultDto SUCCESS_RESULT = new ResultDto(SUCCESS_CODE, SUCCESS_MESSAGE);

    private static final String PATTERN = "yyyy-MM-dd HH:mm:sss";

    private Integer code;

    private String message;

    private T data;

    private String timestamp;

    public static ResultDto success(){
        return new ResultDto(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    private ResultDto(){}

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
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
        this.data = data;
        this.timestamp = DateFormatUtils.format(new Date(), PATTERN);
    }

}
