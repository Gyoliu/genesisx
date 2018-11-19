package com.x.web;

import com.alibaba.fastjson.JSON;
import com.x.dao.entity.SysUser;
import com.x.dto.ResultDto;
import com.x.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/8 17:11
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/")
public class OauthController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private ISysUserService sysUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/user/online", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto index(){
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        ArrayList<SysUser> sysUsers = new ArrayList<>();
        allPrincipals.forEach(x -> {
            sysUsers.add(sysUserService.selectByUsername(x.toString()));
        });
        return new ResultDto(sysUsers) ;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello" ;
    }

    @RequestMapping("/api")
    public String api() {
        return "api";
    }

    @RequestMapping("/")
    public ResultDto home(HttpServletRequest request) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser == null){
            return new ResultDto(HttpStatus.UNAUTHORIZED);
        }
        String username = currentUser.getName();
        SysUser sysUser = sysUserService.selectByUsername(username);
        return new ResultDto(sysUser);
    }

}