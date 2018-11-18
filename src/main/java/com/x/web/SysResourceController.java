package com.x.web;

import com.x.dao.SysResourceMapper;
import com.x.dao.SysRoleMapper;
import com.x.dao.entity.SysResource;
import com.x.dao.entity.SysRole;
import com.x.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:SysResourceController
 * Description:
 *
 * @Author Gyo
 * @Date 2018/11/16 22:11
 */
@RestController
public class SysResourceController {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/resource/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto resources(){
        List<SysResource> sysResources = sysResourceMapper.selectList();
        return new ResultDto(sysResources) ;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/role/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto reols(){
        List<SysRole> sysRoles = sysRoleMapper.selectList();
        return new ResultDto(sysRoles) ;
    }

}
