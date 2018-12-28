package com.genesis.x.dao;

import com.genesis.x.dao.entity.SystemLog;

import java.util.List;

public interface SystemLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    SystemLog selectByPrimaryKey(Integer id);

    List<SystemLog> selectSystemLogs(SystemLog record);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKey(SystemLog record);
}