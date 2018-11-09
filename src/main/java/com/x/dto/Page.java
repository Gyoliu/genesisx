package com.x.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/8 11:55
 * @Description:
 */
@Data
public class Page {

    private int pageNum;
    private int pageSize;
    private long total;

    private List<String> orderBy;
    private List<Boolean> asc;

}