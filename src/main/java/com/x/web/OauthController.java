package com.x.web;

import com.alibaba.fastjson.JSON;
import com.x.dao.entity.SysUser;
import com.x.dto.ResultDto;
import com.x.security.LoginAuthenticationFilter;
import com.x.security.Oauth2Property;
import com.x.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    private InMemoryTokenStore inMemoryTokenStore;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private Oauth2Property oauth2Property;

    @RequestMapping("/")
    public ResultDto home(HttpServletRequest request, HttpServletResponse response) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new ResultDto(HttpStatus.UNAUTHORIZED);
        }
        String username = currentUser.getName();
        SysUser sysUser = sysUserService.selectByUsername(username);
        return new ResultDto(sysUser);
    }

    @RequestMapping(value = "/system/login", method = RequestMethod.GET)
    public ResultDto login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(username);
        System.out.println(password);
        System.out.println(IOUtils.toString(request.getInputStream()));
        return new ResultDto("");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/user/online", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto index(){
        ArrayList<SysUser> sysUsers = new ArrayList<>();
        Collection<OAuth2AccessToken> tokensByClientId = inMemoryTokenStore.findTokensByClientId(oauth2Property.getClientId());
        tokensByClientId.forEach(x -> {
            Map<String, Object> additionalInformation = x.getAdditionalInformation();
            if(!CollectionUtils.isEmpty(additionalInformation)){
                Authentication authentication = (Authentication)additionalInformation.get(LoginAuthenticationFilter.authenticateKey);
                sysUsers.add(sysUserService.selectByUsername(authentication.getPrincipal().toString()));
            }
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