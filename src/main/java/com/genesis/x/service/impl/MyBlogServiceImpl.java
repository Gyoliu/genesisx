package com.genesis.x.service.impl;

import com.genesis.x.dao.entity.MyBlog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.genesis.x.dao.MyBlogMapper;
import com.genesis.x.dto.Page;
import com.genesis.x.service.IMyBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/28 11:50
 * @Description:
 */
@Service
public class MyBlogServiceImpl implements IMyBlogService {

    @Autowired
    private MyBlogMapper myBlogMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        int i = myBlogMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int insert(MyBlog record) {
        int insert = myBlogMapper.insert(record);
        return insert;
    }

    @Override
    public int insertSelective(MyBlog record) {
        int i = myBlogMapper.insertSelective(record);
        return i;
    }

    @Override
    public MyBlog selectByPrimaryKey(Integer id) {
        MyBlog myBlog = myBlogMapper.selectByPrimaryKey(id);
        return myBlog;
    }

    @Override
    public PageInfo<MyBlog> selectBlogList(MyBlog record, Page page) {
        PageHelper.startPage(page.getNum(), page.getSize(), true);
        PageHelper.orderBy(page.buildOrderBy());
        List<MyBlog> myBlogs = myBlogMapper.selectBlogList(record);
        PageInfo<MyBlog> myBlogPageInfo = new PageInfo<>(myBlogs);
        return myBlogPageInfo;
    }

    @Override
    public int updateByPrimaryKeySelective(MyBlog record) {
        int i = myBlogMapper.updateByPrimaryKeySelective(record);
        return i;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(MyBlog record) {
        int i = myBlogMapper.updateByPrimaryKeyWithBLOBs(record);
        return i;
    }

    @Override
    public int updateByPrimaryKey(MyBlog record) {
        int i = myBlogMapper.updateByPrimaryKey(record);
        return i;
    }
}