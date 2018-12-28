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
        if(CollectionUtils.isEmpty(page.getOrderBy())){
            PageHelper.orderBy(" create_date " + Page.OrderByEnum.DESC.name());
        } else {
            List<String> orderBy = page.getOrderBy();
            List<Boolean> asc = page.getAsc();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<orderBy.size();i++){
                boolean bo = false;
                if(CollectionUtils.isEmpty(asc) || asc.size() <= i){
                    bo = true;
                }
                stringBuilder.append(orderBy.get(i) + " ").append(bo ? Page.OrderByEnum.DESC.name() + " ,": Page.OrderByEnum.ASC.name() + " ,");
            }
            if(stringBuilder.length() > 0){
                stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            PageHelper.orderBy(stringBuilder.toString());
        }
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