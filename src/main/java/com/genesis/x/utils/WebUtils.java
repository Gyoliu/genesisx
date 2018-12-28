package com.genesis.x.utils;

import com.genesis.x.dto.SystemUserDto;
import com.genesis.x.config.SpringContextHolder;
import com.genesis.x.dao.entity.SysUser;
import com.genesis.x.security.Oauth2Property;
import com.genesis.x.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author: liuxing
 * @Date: 2018/12/12 15:48
 * @Description:
 */
@Slf4j
public class WebUtils {

    public static final String SESSION_USER_KEY = "session_system_user_key";

    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            return ((ServletRequestAttributes) attributes).getRequest();
        } else {
            log.error("[请求信息不存在:RequestAttributes not exist]");
            return null;
        }
    }

    public static HttpSession setHttpSession() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession(false);
        return session;
    }

    public static SystemUserDto getSessionUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser == null){
            throw new RuntimeException(HttpStatus.UNAUTHORIZED.toString());
        }
        String username = currentUser.getName();
        ISysUserService bean = SpringContextHolder.getBean(ISysUserService.class);
        InMemoryTokenStore inMemoryTokenStore = SpringContextHolder.getBean(InMemoryTokenStore.class);
        Oauth2Property oauth2Property = SpringContextHolder.getBean(Oauth2Property.class);
        SysUser sysUser = bean.selectByUsername(username);
        Collection<OAuth2AccessToken> tokensByClientIdAndUserName = inMemoryTokenStore.findTokensByClientIdAndUserName(oauth2Property.getClientId(), username);
        OAuth2AccessToken oAuth2AccessToken = null;
        if(tokensByClientIdAndUserName != null && tokensByClientIdAndUserName.size() > 0){
            oAuth2AccessToken = tokensByClientIdAndUserName.iterator().next();
        }
        SystemUserDto systemUserDto = new SystemUserDto(sysUser, oAuth2AccessToken);
        return systemUserDto;
    }

}