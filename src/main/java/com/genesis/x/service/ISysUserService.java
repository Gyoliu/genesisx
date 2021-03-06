package com.genesis.x.service;

import com.genesis.x.dao.entity.SysUser;
import com.genesis.x.dao.entity.SysUserInfo;
import com.genesis.x.dto.Page;
import com.genesis.x.dto.ResultDto;
import com.genesis.x.dto.SystemUserDto;
import com.genesis.x.vo.ResetPasswordVo;

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

    public List<SysUser> selectUsers(SysUser record, Page page);

    public int updateByPrimaryKeySelective(SysUser record);

    public int updateByPrimaryKey(SysUser record);

    int updatePasswordByUsername(SysUser record);

    int updateUserInfo(SysUserInfo sysUserInfo);

    int insertUserInfo(SysUserInfo sysUserInfo);

    ResultDto register(SystemUserDto systemUserDto);

    ResultDto resetPassword(ResetPasswordVo resetPasswordVo);
}