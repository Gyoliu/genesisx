package com.genesis.x.dao;

import com.genesis.x.dao.entity.SysUserInfo;

import java.util.List;

public interface SysUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserInfo record);

    int insertSelective(SysUserInfo record);

    SysUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserInfo record);

    int updateByPrimaryKeyWithBLOBs(SysUserInfo record);

    int updateByPrimaryKey(SysUserInfo record);

    List<SysUserInfo> selectList();
}