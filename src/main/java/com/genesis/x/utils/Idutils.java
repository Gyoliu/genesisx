package com.genesis.x.utils;

import java.util.UUID;

/**
 * @Author liuxing
 * @Date 2020/3/13 9:55
 * @Version 1.0
 * @Description:
 */
public class Idutils {
    
    public static String getUUID15(){
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        return s.substring(0, 15);
    }
    
}
