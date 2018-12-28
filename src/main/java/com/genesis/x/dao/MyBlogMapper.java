package com.genesis.x.dao;

import com.genesis.x.dao.entity.MyBlog;

import java.util.List;

public interface MyBlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyBlog record);

    int insertSelective(MyBlog record);

    MyBlog selectByPrimaryKey(Integer id);

    List<MyBlog> selectBlogList(MyBlog record);

    int updateByPrimaryKeySelective(MyBlog record);

    int updateByPrimaryKeyWithBLOBs(MyBlog record);

    int updateByPrimaryKey(MyBlog record);
}