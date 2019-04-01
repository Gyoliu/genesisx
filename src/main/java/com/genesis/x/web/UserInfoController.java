package com.genesis.x.web;

import com.genesis.x.dao.entity.SysUser;
import com.genesis.x.dto.EnumError;
import com.genesis.x.dto.FormValidateGroup;
import com.genesis.x.dto.ResultDto;
import com.genesis.x.dto.SystemUserDto;
import com.genesis.x.security.Oauth2Property;
import com.genesis.x.service.ISysUserService;
import com.genesis.x.vo.ResetPasswordVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

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
            return new ResultDto(EnumError.USER_NOT_EXIST);
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

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto register(@RequestBody @Validated(FormValidateGroup.Add.class) SystemUserDto systemUserDto){
        ResultDto register = sysUserService.register(systemUserDto);
        return register;
    }

    @RequestMapping(value = "/reset/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto resetPassword(@RequestBody @Validated ResetPasswordVo resetPasswordVo){
        ResultDto register = sysUserService.resetPassword(resetPasswordVo);
        return register;
    }

}
