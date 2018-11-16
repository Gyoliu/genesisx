package com.x.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: liuxing
 * @Date: 2018/11/8 17:11
 * @Description:
 */
@RestController
@RequestMapping("/")
public class OauthController {

    @RequestMapping("/")
    public String index(){
        return "index" ;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello" ;
    }

    @RequestMapping("/api")
    public String api() {
        return "api";
    }

}