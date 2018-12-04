package com.x.utils;

import com.x.dto.SystemUserDto;
import com.x.vo.QueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: liuxing
 * @Date: 2018/12/4 17:13
 * @Description:
 */
@Slf4j
public class QueryUtils {

    private static final String DATEPATTERN = "yyyy-MM-dd HH:mm:ss";

    public static <T> void convert2Dto(QueryVo queryVo, T t){
        Field field = ReflectionUtils.findField(t.getClass(), queryVo.getSearchKey());
        if(field != null){
            field.setAccessible(true);
            Object value = dataTypeConvert(field.getType(), queryVo.getSearchValue());
            ReflectionUtils.setField(field, t, value);
        }
    }

    public static Object dataTypeConvert(Class cls, String queryValue){
        Object value = "";
        if(String.class.equals(cls)){
            value = queryValue;
        } else if(Float.class.equals(cls)){
            value = Float.parseFloat(queryValue);
        }else if(Double.class.equals(cls)){
            value = Double.parseDouble(queryValue);
        }else if(Integer.class.equals(cls)){
            value = Integer.parseInt(queryValue);
        }else if(Long.class.equals(cls)){
            value = Long.parseLong(queryValue);
        }else if(Date.class.equals(cls)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEPATTERN);
            try {
                value = simpleDateFormat.parse(queryValue);
            } catch (ParseException e) {
                value = null;
                log.error("数据转换异常：{}", e.getMessage());
            }
        }else if(Boolean.class.equals(cls)){
            value = Boolean.parseBoolean(queryValue);
        }
        return value;
    }
}