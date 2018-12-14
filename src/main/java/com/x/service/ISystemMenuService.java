package com.x.service;

import com.x.dao.entity.SystemMenu;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/12 10:07
 * @Description:
 */
public interface ISystemMenuService {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemMenu record);

    int insertSelective(SystemMenu record);

    SystemMenu selectByPrimaryKey(Integer id);

    List<SystemMenu> selectSystemMenus();

    int updateByPrimaryKeySelective(SystemMenu record);

    int updateByPrimaryKey(SystemMenu record);

}