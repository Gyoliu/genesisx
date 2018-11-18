package com.x.dao;

import com.x.dao.entity.SysResource;

import java.util.List;

public interface SysResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysResource record);

    int insertSelective(SysResource record);

    SysResource selectByPrimaryKey(Integer id);

    List<SysResource> selectList();

    List<SysResource> selectByRoleId(Integer roleId);

    int updateByPrimaryKeySelective(SysResource record);

    int updateByPrimaryKey(SysResource record);
}