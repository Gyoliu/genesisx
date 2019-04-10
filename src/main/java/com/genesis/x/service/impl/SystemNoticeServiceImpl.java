package com.genesis.x.service.impl;

import com.genesis.x.dao.SystemNoticeMapper;
import com.genesis.x.dao.SystemNoticeUserRelMapper;
import com.genesis.x.dao.entity.SysUser;
import com.genesis.x.dao.entity.SystemNotice;
import com.genesis.x.dao.entity.SystemNoticeUserRel;
import com.genesis.x.dto.Page;
import com.genesis.x.dto.ResultDto;
import com.genesis.x.dto.SystemNoticeDto;
import com.genesis.x.service.ISysUserService;
import com.genesis.x.service.ISystemNoticeService;
import com.genesis.x.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liuxing
 * @Date: 2019/4/2 15:40
 * @Description:
 */
@Service
public class SystemNoticeServiceImpl implements ISystemNoticeService {

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    @Autowired
    private SystemNoticeUserRelMapper systemNoticeUserRelMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public ResultDto insertNotice(SystemNotice systemNotice) {
        systemNoticeMapper.insert(systemNotice);
        return new ResultDto(systemNotice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDto insertNotices(SystemNotice systemNotice) {
        Integer currentUserId = WebUtils.getSessionUser().getId();
        systemNotice.setCreator(currentUserId);
        systemNotice.setCreateTime(new Date());
        systemNoticeMapper.insert(systemNotice);
        Integer noticeId = systemNotice.getId();

        Integer noticeType = systemNotice.getNoticeType();
        if(SystemNotice.NoticeType.ALL.getType() == noticeType){
            SysUser sysUser = new SysUser();
            sysUser.setEnable(true);
            sysUser.setLocking(false);
            // 查询全部用户
            List<SysUser> sysUsers = sysUserService.selectUsers(sysUser, new Page(0, 0));
            if(!CollectionUtils.isEmpty(sysUsers)){
                List<SystemNoticeUserRel> collect = sysUsers.stream().map(x -> {
                    SystemNoticeUserRel systemNoticeUserRel = new SystemNoticeUserRel();
                    systemNoticeUserRel.setNoticeId(noticeId);
                    systemNoticeUserRel.setUserId(x.getId());
                    systemNoticeUserRel.setCreateTime(new Date());
                    systemNoticeUserRel.setCreator(currentUserId);
                    systemNoticeUserRel.setStatus(0);
                    systemNoticeUserRel.setNoticeType(noticeType);
                    return systemNoticeUserRel;
                }).collect(Collectors.toList());
                this.insertNoticeUserRel(collect);
            }
        } else if(SystemNotice.NoticeType.SPECIFIC_USER.getType() == noticeType){
            Integer[] userIds = systemNotice.getUserIds();
            List<SystemNoticeUserRel> collect = Arrays.stream(userIds).map(x -> {
                SystemNoticeUserRel systemNoticeUserRel = new SystemNoticeUserRel();
                systemNoticeUserRel.setNoticeId(noticeId);
                systemNoticeUserRel.setUserId(x);
                systemNoticeUserRel.setCreateTime(new Date());
                systemNoticeUserRel.setCreator(currentUserId);
                systemNoticeUserRel.setStatus(0);
                systemNoticeUserRel.setNoticeType(noticeType);
                return systemNoticeUserRel;
            }).collect(Collectors.toList());
            this.insertNoticeUserRel(collect);
        }
        return ResultDto.success();
    }

    @Override
    public ResultDto insertNoticeUserRel(List<SystemNoticeUserRel> systemNoticeUserRels) {
        systemNoticeUserRelMapper.insertBatch(systemNoticeUserRels);
        return ResultDto.success();
    }

    @Override
    public ResultDto countUserNotice(SystemNoticeUserRel systemNoticeUserRel) {
        int i = systemNoticeUserRelMapper.countUserNotice(systemNoticeUserRel);
        return new ResultDto(i);
    }

    @Override
    public ResultDto selectUserNotice(SystemNoticeUserRel systemNoticeUserRel, Page page) {
        PageHelper.startPage(page.getNum(), page.getSize());
        PageHelper.orderBy(page.buildOrderBy());
        List<SystemNoticeDto> systemNoticeDtos = systemNoticeUserRelMapper.selectUserNotice(systemNoticeUserRel);
        PageInfo<SystemNoticeDto> pageInfo = new PageInfo<>(systemNoticeDtos);
        return new ResultDto(pageInfo);
    }

    @Override
    public ResultDto selectNoticeById(Integer noticeId) {
        SystemNotice systemNotice = systemNoticeMapper.selectByPrimaryKey(noticeId);
        return new ResultDto(systemNotice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDto readNotice(List<SystemNoticeUserRel> systemNoticeUserRel) {
        systemNoticeUserRel.forEach(x -> {
            x.setStatus(1);
            systemNoticeUserRelMapper.updateByPrimaryKeySelective(x);
        });
        return ResultDto.success();
    }

    @Override
    public ResultDto unReadNotice(List<SystemNoticeUserRel> systemNoticeUserRel) {
        systemNoticeUserRel.forEach(x -> {
            x.setStatus(0);
            systemNoticeUserRelMapper.updateByPrimaryKeySelective(x);
        });
        return ResultDto.success();
    }

    @Override
    public ResultDto deleteNotice(List<SystemNoticeUserRel> systemNoticeUserRel) {
        systemNoticeUserRel.forEach(x -> {
            x.setStatus(2);
            systemNoticeUserRelMapper.updateByPrimaryKeySelective(x);
        });
        return ResultDto.success();
    }
}