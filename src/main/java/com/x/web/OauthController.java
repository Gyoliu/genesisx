package com.x.web;

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

    /*@RequestMapping(method = RequestMethod.POST, value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void login(@RequestBody SysUser user){

    }*/

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Mono<String> home(){
        return Mono.just("home ,Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public Mono<String> hello(){
        return Mono.just("Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/loginError")
    public Mono<String> loginError(){
        return Mono.just("login error ,Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/success")
    public Mono<String> success(){
        return Mono.just("login success ,Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}