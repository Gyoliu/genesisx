package com.x.web;

import com.x.dao.entity.SysUser;
import com.x.dto.ResultDto;
import com.x.dto.SystemUserDto;
import com.x.security.Oauth2Property;
import com.x.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.x.dto.EnumError.USER_NOT_EXIST;

/**
 * ClassName:UserInfoController
 * Description:
 *
 * @Author Gyo
 * @Date 2018/11/16 21:58
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private InMemoryTokenStore inMemoryTokenStore;

    @Autowired
    private Oauth2Property oauth2Property;

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto userInfo(@RequestParam("username") String username){
        SysUser sysUser = sysUserService.selectByUsername(username);
        if(sysUser == null){
            return new ResultDto(USER_NOT_EXIST);
        }
        SystemUserDto systemUserDto = new SystemUserDto(sysUser);
        Collection<OAuth2AccessToken> tokensByClientIdAndUserName = inMemoryTokenStore.findTokensByClientIdAndUserName(oauth2Property.getClientId(), username);
        OAuth2AccessToken oAuth2AccessToken = null;
        if(tokensByClientIdAndUserName != null && tokensByClientIdAndUserName.size() > 0){
            oAuth2AccessToken = tokensByClientIdAndUserName.iterator().next();
        }
        systemUserDto.setOAuth2AccessToken(oAuth2AccessToken);
        return new ResultDto(systemUserDto) ;
    }

}
