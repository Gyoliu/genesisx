package com.genesis.x.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author liuxing
 * @Date 2020/3/12 10:22
 * @Version 1.0
 * @Description: 接收邮件的数据的存储
 */
@Data
@NoArgsConstructor
public class EmailDto implements Serializable {

    private String messageId;
    private String subject;
    private String htmlContent;
    private String plainTextContent;
    private String from;
    private String personal;
    private String to;
    private String replyTo;
    private String cc;
    private String bcc;
    private Date sendDate;
    private Date receiveDate;
    private boolean isSeen;
    /**
     * 1(High):紧急  3:普通(Normal)  5:低(Low)
     */
    private int priority;
    private boolean replySign;
    private int size;
    private boolean isContainerAttachment;
    private Map<String,InputStream> files;

}
