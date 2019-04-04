package com.genesis.x.service.impl;

import com.alibaba.fastjson.JSON;
import com.genesis.x.dao.SysUserInfoMapper;
import com.genesis.x.dao.SysUserMapper;
import com.genesis.x.dao.entity.SysUserInfo;
import com.genesis.x.dao.entity.SystemLog;
import com.genesis.x.dto.ResultDto;
import com.genesis.x.dto.SystemUserDto;
import com.genesis.x.service.ISystemLogService;
import com.genesis.x.utils.PasswordUtils;
import com.genesis.x.vo.ResetPasswordVo;
import com.github.pagehelper.PageHelper;
import com.genesis.x.dao.entity.SysUser;
import com.genesis.x.dto.Page;
import com.genesis.x.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<SysUser> selectUsers(SysUser record, Page page){
        PageHelper.startPage(page.getNum(), page.getSize(), true);
        return sysUserMapper.selectUsers(record);
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
        // 加密密码
        String encodePwd = PasswordUtils.encode(sysUser.getPassword());
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
    @Transactional(rollbackFor = Exception.class)
    public ResultDto resetPassword(ResetPasswordVo resetPasswordVo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return new ResultDto(401, "未登入");
        }
        String name = authentication.getName();
        SysUser sysUser = this.selectByUsername(name);
        if(sysUser == null){
            return new ResultDto(400, "用户不存在");
        }
        boolean matches = PasswordUtils.matches(resetPasswordVo.getPassword(), sysUser.getPassword());
        if(!matches){
            return new ResultDto(400, "旧密码不正确");
        }
        // 这个不要md5 前端已经md5过
        String encode = resetPasswordVo.getResetPassword();
        SysUser sysUser1 = new SysUser();
        sysUser1.setUsername(name);
        sysUser1.setPassword(encode);
        sysUser1.setModifierId(sysUser.getId());
        sysUser1.setModifyDate(new Date());
        this.updatePasswordByUsername(sysUser1);

        SystemLog systemLog = new SystemLog();
        systemLog.setCreator(sysUser.getId());
        systemLog.setType("update_password");
        systemLog.setBeforeData(JSON.toJSONString(sysUser));
        systemLog.setAfterData(JSON.toJSONString(resetPasswordVo));
        systemLogService.insert(systemLog);

        return ResultDto.success();
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