package com.genesis.x.web;

import com.genesis.x.dao.entity.MyBlog;
import com.genesis.x.dto.ResultDto;
import com.genesis.x.utils.QueryUtils;
import com.github.pagehelper.PageInfo;
import com.genesis.x.service.IMyBlogService;
import com.genesis.x.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: liuxing
 * @Date: 2018/12/28 15:30
 * @Description:
 */
@RestController
@RequestMapping("/blog")
public class MyBlogController {

    @Autowired
    private IMyBlogService myBlogService;

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    ResultDto deleteByPrimaryKey(@PathVariable("id") Integer id){
        int i = myBlogService.deleteByPrimaryKey(id);
        return new ResultDto(i);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto insert(@RequestBody MyBlog record){
        int i = myBlogService.insert(record);
        return new ResultDto(i);
    }

    @RequestMapping(value = "/insertSelective", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto insertSelective(MyBlog record){
        int i = myBlogService.insertSelective(record);
        return new ResultDto(i);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto selectByPrimaryKey(@PathVariable("id") Integer id){
        MyBlog myBlog = myBlogService.selectByPrimaryKey(id);
        return new ResultDto(myBlog);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto selectBlogList(@RequestBody QueryVo queryVo){
        MyBlog record = new MyBlog();
        QueryUtils.convert2Dto(queryVo, record);
        PageInfo<MyBlog> myBlogPageInfo = myBlogService.selectBlogList(record, queryVo.getPage());
        return new ResultDto(myBlogPageInfo);
    }

    @RequestMapping(value = "/updateSelective", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto updateByPrimaryKeySelective(@RequestBody MyBlog record){
        int i = myBlogService.updateByPrimaryKeySelective(record);
        return new ResultDto(i);
    }

    @RequestMapping(value = "/updateWithBlobs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto updateByPrimaryKeyWithBLOBs(@RequestBody MyBlog record){
        int i = myBlogService.updateByPrimaryKeyWithBLOBs(record);
        return new ResultDto(i);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto updateByPrimaryKey(@RequestBody MyBlog record){
        int i = myBlogService.updateByPrimaryKey(record);
        return new ResultDto(i);
    }

}