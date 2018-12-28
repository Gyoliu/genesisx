package com.genesis.x.service;

import com.genesis.x.dao.entity.MyBlog;
import com.genesis.x.dto.Page;
import com.github.pagehelper.PageInfo;

/**
 * @Author: liuxing
 * @Date: 2018/12/28 11:49
 * @Description:
 */
public interface IMyBlogService {

    int deleteByPrimaryKey(Integer id);

    int insert(MyBlog record);

    int insertSelective(MyBlog record);

    MyBlog selectByPrimaryKey(Integer id);

    PageInfo<MyBlog> selectBlogList(MyBlog record, Page page);

    int updateByPrimaryKeySelective(MyBlog record);

    int updateByPrimaryKeyWithBLOBs(MyBlog record);

    int updateByPrimaryKey(MyBlog record);

}