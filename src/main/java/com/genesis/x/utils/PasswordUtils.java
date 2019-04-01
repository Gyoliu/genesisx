package com.genesis.x.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: liuxing
 * @Date: 2019/3/29 17:20
 * @Description:
 */
public class PasswordUtils {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String pwd){
        String md5Pwd = DigestUtils.md5Hex(pwd);
        String encodePwd = passwordEncoder.encode(md5Pwd);
        return encodePwd;
    }

    /**
     *  密码比对
     * @param pwd 输入的密码
     * @param matchPwd 数据库的密码
     * @return
     */
    public static boolean matches(String pwd, String matchPwd){
        boolean matches = passwordEncoder.matches(pwd, matchPwd);
        return matches;
    }

}