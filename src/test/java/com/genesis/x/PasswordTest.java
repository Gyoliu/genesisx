package com.genesis.x;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
        String encode = passwordEncoder.encode("genesisx");
        System.out.println(encode);

        boolean matches = passwordEncoder.matches("genesisx", encode);
        System.out.println(matches);

        //前端提交的密码 s
        String password = "123456";
        String s = DigestUtils.md5Hex(password);
        System.out.println(s);

        //数据库保存的密码 encode1
        String encode1 = passwordEncoder.encode(s);
        System.out.println(encode1);
        //匹配
        System.out.println(passwordEncoder.matches(s, encode1));
    }

    @Test
    public void guid(){
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").length());
        System.out.println(DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss"));
        System.out.println(new ArrayList<String>(){{add("asdfsad");add("asdfsad1");}});
    }

    @Test
    public void testLinkedMap(){
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(3, "3");
        map.put(1, "1");
        map.forEach((k, v) -> System.out.println(k));
    }

}