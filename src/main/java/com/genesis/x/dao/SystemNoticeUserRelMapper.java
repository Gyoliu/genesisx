package com.genesis.x.dao;

import com.genesis.x.dao.entity.SystemNoticeUserRel;
import com.genesis.x.dto.SystemNoticeDto;

import java.util.List;

public interface SystemNoticeUserRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemNoticeUserRel record);

    int countUserNotice(SystemNoticeUserRel record);

    List<SystemNoticeDto> selectUserNotice(SystemNoticeUserRel record);

    void insertBatch(List<SystemNoticeUserRel> records);

    int insertSelective(SystemNoticeUserRel record);

    SystemNoticeUserRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemNoticeUserRel record);

    int updateNoticeUserRel(SystemNoticeUserRel record);

    int updateByPrimaryKey(SystemNoticeUserRel record);
}