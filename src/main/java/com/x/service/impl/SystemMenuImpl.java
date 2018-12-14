package com.x.service.impl;

import com.x.dao.SystemMenuMapper;
import com.x.dao.entity.SysUser;
import com.x.dao.entity.SystemMenu;
import com.x.dto.SystemUserDto;
import com.x.service.ISysUserService;
import com.x.service.ISystemMenuService;
import com.x.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/12 10:07
 * @Description:
 */
@Slf4j
@Service
public class SystemMenuImpl implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        int i = systemMenuMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int insert(SystemMenu record) {
        int insert = systemMenuMapper.insert(record);
        return insert;
    }

    @Override
    public int insertSelective(SystemMenu record) {
        int i = systemMenuMapper.insertSelective(record);
        return i;
    }

    @Override
    public SystemMenu selectByPrimaryKey(Integer id) {
        SystemMenu systemMenu = systemMenuMapper.selectByPrimaryKey(id);
        return systemMenu;
    }

    @Override
    public int updateByPrimaryKeySelective(SystemMenu record) {
        int i = systemMenuMapper.updateByPrimaryKeySelective(record);
        return i;
    }

    @Override
    public int updateByPrimaryKey(SystemMenu record) {
        int i = systemMenuMapper.updateByPrimaryKey(record);
        return i;
    }

    @Override
    public List<SystemMenu> selectSystemMenus() {
        SystemUserDto sessionUser = WebUtils.getSessionUser();
        if(sessionUser == null || sessionUser.getId() == null){
            return null;
        }
        SystemMenu systemMenuParam = new SystemMenu();
        systemMenuParam.setUserId(sessionUser.getId());
        systemMenuParam.setLevel(1);
        List<SystemMenu> systemMenus = systemMenuMapper.selectSystemMenus(systemMenuParam);
        systemMenus.forEach(x -> menuParent(x, sessionUser.getId()));
        return systemMenus;
    }

    private SystemMenu menuParent(SystemMenu systemMenu, Integer userId){
        SystemMenu systemMenuParam = new SystemMenu();
        systemMenuParam.setUserId(userId);
        systemMenuParam.setParent(systemMenu.getId());
        List<SystemMenu> systemMenus = systemMenuMapper.selectSystemMenus(systemMenuParam);
        systemMenu.setChildrenMenus(systemMenus);
        systemMenu.setUserId(userId);
        if(!CollectionUtils.isEmpty(systemMenus)){
            systemMenus.forEach(x -> menuParent(x, userId));
        }
        return systemMenu;
    }

}