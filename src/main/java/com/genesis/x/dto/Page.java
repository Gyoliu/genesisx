package com.genesis.x.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/8 11:55
 * @Description:
 */
@Data
public class Page {

    public enum OrderByEnum{
        DESC,
        ASC
    }

    private int num;
    private int size;
    private long total;

    private List<String> orderBy;
    private List<Boolean> asc;

}