package com.x.web;

import com.x.dao.entity.SysUser;
import com.x.dto.ResultDto;
import com.x.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /*@RequestMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultDto index(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password){
        log.info("username:{} - password:{}", username, password);
        return new ResultDto("success") ;
    }*/

    @PreAuthorize("hasRole('ADMIN1')")
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

}