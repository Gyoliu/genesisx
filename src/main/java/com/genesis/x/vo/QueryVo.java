package com.genesis.x.vo;

import com.genesis.x.dto.Page;
import lombok.Data;

/**
 * @Author: liuxing
 * @Date: 2018/12/4 17:11
 * @Description:
 */
@Data
public class QueryVo {

    private String searchKey;
    private String searchValue;

    private Page page;

}