package com.x.web;

import com.x.dao.entity.SysUser;
import com.x.dto.ResultDto;
import com.x.dto.SystemUserDto;
import com.x.security.LoginAuthenticationFilter;
import com.x.security.Oauth2Property;
import com.x.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
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

    /**
     * 登入成功之后，默认转发到/
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/")
    public ResultDto home(HttpServletRequest request, HttpServletResponse response) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new ResultDto(HttpStatus.UNAUTHORIZED);
        }
        String username = currentUser.getName();

        SysUser sysUser = sysUserService.selectByUsername(username);
        Collection<OAuth2AccessToken> tokensByClientIdAndUserName = inMemoryTokenStore.findTokensByClientIdAndUserName(oauth2Property.getClientId(), username);
        OAuth2AccessToken oAuth2AccessToken = null;
        if(tokensByClientIdAndUserName != null && tokensByClientIdAndUserName.size() > 0){
            oAuth2AccessToken = tokensByClientIdAndUserName.iterator().next();
        }
        return new ResultDto(new SystemUserDto(sysUser, oAuth2AccessToken));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/user/online", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto index(){
        ArrayList<SystemUserDto> systemUserDtos = new ArrayList<>();
        Collection<OAuth2AccessToken> tokensByClientId = inMemoryTokenStore.findTokensByClientId(oauth2Property.getClientId());
        tokensByClientId.forEach(x -> {
            Map<String, Object> additionalInformation = x.getAdditionalInformation();
            if(!CollectionUtils.isEmpty(additionalInformation)){
                Authentication authentication = (Authentication)additionalInformation.get(LoginAuthenticationFilter.authenticateKey);
                SysUser sysUser = sysUserService.selectByUsername(authentication.getPrincipal().toString());
                systemUserDtos.add(new SystemUserDto(sysUser));
            }
        });
        return new ResultDto(systemUserDtos) ;
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