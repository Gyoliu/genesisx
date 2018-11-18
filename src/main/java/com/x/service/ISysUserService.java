package com.x.service;

import com.x.dao.entity.SysUser;
import com.x.dto.Page;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/8 14:53
 * @Description:
 */
public interface ISysUserService {

    public int deleteByPrimaryKey(Integer id);

    public int insert(SysUser record);

    public int insertSelective(SysUser record);

    public SysUser selectByPrimaryKey(Integer id);

    public SysUser selectByUsername(String username);

    public SysUser selectByUserJoin(Integer id);

    public List<SysUser> selectUsers(Page page);

    public int updateByPrimaryKeySelective(SysUser record);

    public int updateByPrimaryKey(SysUser record);

    int updatePasswordByUsername(SysUser record);

}