package com.x.web;

import com.x.dao.entity.SysUser;
import com.x.dto.ResultDto;
import com.x.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/info/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto userInfo(@PathVariable("username") String username){
        SysUser sysUser = sysUserService.selectByUsername(username);
        return new ResultDto(sysUser) ;
    }

}
