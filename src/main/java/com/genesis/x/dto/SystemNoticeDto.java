package com.genesis.x.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liuxing
 * @Date: 2019/4/4 11:37
 * @Description:
 */
@Data
public class SystemNoticeDto {

    private Integer id;

    private String title;

    private Integer creator;

    private Date createTime;

    private String content;

    private Integer noticeType;

    private Integer userId;

    private Integer status;

}