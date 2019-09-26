package com.genesis.x.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liuxing
 * @Date: 2019/4/17 10:20
 * @Description:
 */
@Data
public class MessageVo {

    /**
     * 谁发送的
     */
    private String from;
    /**
     * 发送给谁
     */
    private String to;
    /**
     * 发送时间
     */
    private Date date;
    /**
     * 地址
     */
    private String local;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 是否是自己的消息
     */
    private boolean self;

}