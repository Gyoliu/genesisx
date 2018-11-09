package com.x.service.impl;

import com.github.pagehelper.PageHelper;
import com.x.dao.SysUserMapper;
import com.x.dao.entity.SysUser;
import com.x.dto.Page;
import com.x.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/8 11:31
 * @Description:
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public int deleteByPrimaryKey(Integer id){
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record){
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record){
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Integer id){
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public SysUser selectByUsername(String username){
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public SysUser selectByUserJoin(Integer id){
        return sysUserMapper.selectByUserJoin(id);
    }

    @Override
    public List<SysUser> selectUsers(Page page){
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
        return sysUserMapper.selectUsers();
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record){
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record){
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updatePasswordByUsername(SysUser record) {
        return sysUserMapper.updatePasswordByUsername(record);
    }
}