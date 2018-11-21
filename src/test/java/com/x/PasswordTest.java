package com.x;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

}