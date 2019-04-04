package com.genesis.x.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;

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

    public Page(){}
    public Page(int num, int size){
        this.num = num;
        this.size = size;
    }

    private int num;
    private int size;
    private long total;

    private List<String> orderBy;
    private List<Boolean> asc;

    public String buildOrderBy(){
        if(CollectionUtils.isEmpty(this.getOrderBy())){
            return " create_date " + Page.OrderByEnum.DESC.name();
        } else {
            List<String> orderBy = this.getOrderBy();
            List<Boolean> asc = this.getAsc();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<orderBy.size();i++){
                boolean bo = false;
                if(CollectionUtils.isEmpty(asc) || asc.size() <= i){
                    bo = true;
                }
                stringBuilder.append(orderBy.get(i) + " ").append(bo ? Page.OrderByEnum.DESC.name() + " ,": Page.OrderByEnum.ASC.name() + " ,");
            }
            if(stringBuilder.length() > 0){
                stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            return stringBuilder.toString();
        }
    }
}