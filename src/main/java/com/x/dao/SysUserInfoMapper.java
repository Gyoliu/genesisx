package com.x.dao;

import com.x.dao.entity.SysUserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserInfo record);

    int insertSelective(SysUserInfo record);

    SysUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserInfo record);

    int updateByPrimaryKeyWithBLOBs(SysUserInfo record);

    int updateByPrimaryKey(SysUserInfo record);
}