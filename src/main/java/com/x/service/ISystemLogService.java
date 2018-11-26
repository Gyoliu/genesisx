package com.x.service;

import com.x.dao.entity.SystemLog;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/11/26 17:06
 * @Description:
 */
public interface ISystemLogService {

    List<SystemLog> selectSystemLogs(SystemLog record);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    SystemLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKey(SystemLog record);

}