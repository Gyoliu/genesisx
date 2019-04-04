package com.genesis.x.dao;

import com.genesis.x.dao.entity.SystemNotice;

public interface SystemNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemNotice record);

    int insertSelective(SystemNotice record);

    SystemNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemNotice record);

    int updateByPrimaryKeyWithBLOBs(SystemNotice record);

    int updateByPrimaryKey(SystemNotice record);
}