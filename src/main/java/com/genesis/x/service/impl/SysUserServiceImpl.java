package com.genesis.x.service.impl;

import com.alibaba.fastjson.JSON;
import com.genesis.x.dao.SysUserInfoMapper;
import com.genesis.x.dao.SysUserMapper;
import com.genesis.x.dao.entity.SysUserInfo;
import com.genesis.x.dao.entity.SystemLog;
import com.genesis.x.dto.ResultDto;
import com.genesis.x.dto.SystemUserDto;
import com.genesis.x.service.ISystemLogService;
import com.github.pagehelper.PageHelper;
import com.genesis.x.dao.entity.SysUser;
import com.genesis.x.dto.Page;
import com.genesis.x.service.ISysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxing
 * @Date: 2018/11/8 11:31
 * @Description:
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    @Autowired
    private ISystemLogService systemLogService;

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
        PageHelper.startPage(page.getNum(), page.getSize(), true);
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

    public static final String INIT_PASSWORD = "123456";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDto register(SystemUserDto systemUserDto) {

        SysUser sysUser1 = this.selectByUsername(systemUserDto.getUsername());
        if(sysUser1 != null){
            return new ResultDto(400,"该用户已存在！");
        }

        //先插入用户信息
        SysUserInfo userInfo = systemUserDto.getUserInfo();
        this.insertUserInfo(userInfo);

        SysUser sysUser = new SysUser();
        sysUser.setCreateDate(new Date());
        sysUser.setUsername(systemUserDto.getUsername());
        sysUser.setLocking(false);
        sysUser.setEnable(true);
        sysUser.setRoleId(2);
        sysUser.setPassword(INIT_PASSWORD);
        String md5Pwd = DigestUtils.md5Hex(sysUser.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePwd = passwordEncoder.encode(md5Pwd);
        sysUser.setPassword(encodePwd);
        sysUser.setUserInfoId(userInfo.getId());
        this.insert(sysUser);

        SystemLog systemLog = new SystemLog();
        systemLog.setCreator(1);
        systemLog.setType(SystemLog.Type.REGISTER.name());
        systemLog.setBeforeData(JSON.toJSONString(systemUserDto));
        systemLog.setAfterData(JSON.toJSONString(systemUserDto));
        systemLogService.insert(systemLog);

        //初始密码
        Map<String, String> map = new HashMap<>(1);
        map.put("password", INIT_PASSWORD);
        return new ResultDto(map);
    }

    @Override
    public int updateUserInfo(SysUserInfo sysUserInfo) {
        int i = sysUserInfoMapper.updateByPrimaryKeyWithBLOBs(sysUserInfo);
        return i;
    }

    @Override
    public int insertUserInfo(SysUserInfo sysUserInfo) {
        int insert = sysUserInfoMapper.insert(sysUserInfo);
        return insert;
    }
}