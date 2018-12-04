package com.x;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: liuxing
 * @Date: 2018/11/21 11:06
 * @Description:
 */
public class PasswordTest {

    @Test
    public void password(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("admin");
        System.out.println(encode);

        boolean matches = passwordEncoder.matches("admin", encode);
        System.out.println(matches);
    }

    @Test
    public void guid(){
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").length());
        System.out.println(DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss"));

    }

}