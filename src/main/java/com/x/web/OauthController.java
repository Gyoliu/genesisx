package com.x.web;

import com.x.dao.entity.SysUser;
import com.x.dto.ResultDto;
import com.x.dto.SystemUserDto;
import com.x.security.LoginAuthenticationFilter;
import com.x.security.Oauth2Property;
import com.x.service.ISysUserService;
import com.x.utils.QueryUtils;
import com.x.vo.QueryVo;
import com.x.vo.UserQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Value("${oauth.cookie.name}")
    private String cookieName;

    @Value("${oauth.cookie.domain}")
    private String domain;

    @Value("${oauth.cookie.maxAge}")
    private int maxAge;

    @Value("${oauth.cookie.httpOnly}")
    private boolean httpOnly;

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
        Cookie cookie = new Cookie(this.cookieName,oAuth2AccessToken == null? null:oAuth2AccessToken.getValue());
        cookie.setDomain(this.domain);
        cookie.setHttpOnly(this.httpOnly);
        cookie.setPath("/");
        cookie.setMaxAge(this.maxAge);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new ResultDto(new SystemUserDto(sysUser, oAuth2AccessToken));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/user/online", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto userOnline(@RequestBody QueryVo queryVo){
        UserQueryVo systemUserDtoQuery = new UserQueryVo();
        boolean filter = queryVo == null || StringUtils.isEmpty(queryVo.getSearchKey()) || StringUtils.isEmpty(queryVo.getSearchValue());
        if(!filter){
            QueryUtils.convert2Dto(queryVo, systemUserDtoQuery);
        }
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
        List<SystemUserDto> systemUserDtos1 = systemUserDtos;
        if(!filter) {
            systemUserDtos1 = systemUserDtos.stream().filter(x -> {
                if (StringUtils.isNotEmpty(systemUserDtoQuery.getUsername())
                        && StringUtils.isNotEmpty(x.getUsername())
                        && x.getUsername().contains(systemUserDtoQuery.getUsername())) {
                    return true;
                } else if (x.getEnable().equals(systemUserDtoQuery.getEnable())) {
                    return true;
                } else if (x.getLocking().equals(systemUserDtoQuery.getLocking())) {
                    return true;
                } else if (StringUtils.isNotEmpty(x.getUserInfo().getFirstName())
                        && StringUtils.isNotEmpty(systemUserDtoQuery.getFirstName())
                        && x.getUserInfo().getFirstName().contains(systemUserDtoQuery.getFirstName())) {
                    return true;
                } else if (StringUtils.isNotEmpty(x.getUserInfo().getEmail())
                        && StringUtils.isNotEmpty(systemUserDtoQuery.getEmail())
                        && x.getUserInfo().getEmail().contains(systemUserDtoQuery.getEmail())) {
                    return true;
                } else if (StringUtils.isNotEmpty(x.getUserInfo().getPhoneNumber())
                        && StringUtils.isNotEmpty(systemUserDtoQuery.getPhoneNumber())
                        && x.getUserInfo().getPhoneNumber().contains(systemUserDtoQuery.getPhoneNumber())) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
        }
        return new ResultDto(systemUserDtos1) ;
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