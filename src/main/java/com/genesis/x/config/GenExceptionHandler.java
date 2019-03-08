package com.genesis.x.config;

import com.alibaba.fastjson.JSON;
import com.genesis.x.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/4 18:46
 * @Description:
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GenExceptionHandler {

    private String buildMessage(Exception exception){
        String message = "";
        if(exception.getCause() != null){
            message = exception.getCause().getMessage();
            log.error("[异常信息:{}]", message);
        } else {
            message = exception.getMessage();
            log.error("[异常信息:{}]", message);
        }
        return message;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResultDto> exceptionHandler(Exception exception) {
        String message = this.buildMessage(exception);
        ResultDto result = new ResultDto(500, message);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResultDto> exceptionHandler(RuntimeException exception) {
        String message = this.buildMessage(exception);
        ResultDto result = new ResultDto(500, message);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 定义参数异常处理器.
     *
     * @param e 当前平台异常参数对象.
     * @return org.springframework.http.ResponseEntity
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResultDto> validateErrorHandler(BindException e) {
        StringBuffer sb = new StringBuffer();
        BindingResult bindingResult = e.getBindingResult();
        if(bindingResult.hasErrors()){
            for(ObjectError objectError : bindingResult.getAllErrors()){
                if(objectError instanceof FieldError){
                    sb.append(((FieldError)objectError).getField() +": ").append(objectError.getDefaultMessage()).append(";");
                } else {
                    sb.append(objectError.getObjectName() +": ").append(objectError.getDefaultMessage()).append(";");
                }
            }
        }
        ResultDto result = new ResultDto(400, sb.toString());
        log.error("[BindException异常信息:{}]", result);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        HashMap<String, String> map = new HashMap<>();
        fieldErrors.forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));
        ResultDto result = new ResultDto(400, JSON.toJSONString(map));
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}