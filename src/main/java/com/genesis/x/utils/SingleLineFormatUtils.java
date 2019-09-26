/*
package com.genesis.x.utils;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

*/
/**
 * @Author: liuxing
 * @Date: 2018/7/11 18:20
 * @Description:
 *//*

public class SingleLineFormatUtils implements MessageFormattingStrategy {

    public SingleLineFormatUtils() {
    }

    */
/**
     * @param connectionId
     * @param now          当前时间戳
     * @param elapsed      执行语句花费的时间
     * @param category     调用的类别
     * @param prepared     声明SQL字符串
     * @param sql          有效的SQL字符串
     *
     * @return
     *//*

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        return String.format("[sql执行时间:{%s}毫秒] - [SQL:{%s}]", elapsed, P6Util.singleLine(sql));
    }
    
}*/
