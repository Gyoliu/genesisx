package com.genesis.x.exception;

/**
 * @Author: liuxing
 * @Date: 2019/6/4 11:19
 * @Description:
 */
public class BasicException extends RuntimeException {

    private int code;
    private String message;

    public BasicException(){
        super();
    }

    public BasicException(String message){
        super(message);
    }

    public BasicException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}