package com.x.dao;

import com.x.dao.entity.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    SysUser selectByUsername(String username);

    SysUser selectByUserJoin(Integer id);

    List<SysUser> selectUsers();

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int updatePasswordByUsername(SysUser record);
}