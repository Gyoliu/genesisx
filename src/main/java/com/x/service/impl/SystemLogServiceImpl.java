package com.x.service.impl;

import com.x.dao.SystemLogMapper;
import com.x.dao.entity.SysUser;
import com.x.dao.entity.SystemLog;
import com.x.service.ISysUserService;
import com.x.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/26 17:11
 * @Description:
 */
@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public List<SystemLog> selectSystemLogs(SystemLog record){
        return systemLogMapper.selectSystemLogs(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return systemLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SystemLog record) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        SysUser sysUser = sysUserService.selectByUsername(name);
        record.setCreateTime(new Date());
        record.setCreator(sysUser.getId());
        return systemLogMapper.insert(record);
    }

    @Override
    public int insertSelective(SystemLog record) {
        return systemLogMapper.insertSelective(record);
    }

    @Override
    public SystemLog selectByPrimaryKey(Integer id) {
        return systemLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SystemLog record) {
        return systemLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SystemLog record) {
        return systemLogMapper.updateByPrimaryKey(record);
    }
}