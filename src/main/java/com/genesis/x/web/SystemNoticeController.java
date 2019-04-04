package com.genesis.x.web;

import com.genesis.x.dao.entity.SystemNotice;
import com.genesis.x.dao.entity.SystemNoticeUserRel;
import com.genesis.x.dto.Page;
import com.genesis.x.dto.ResultDto;
import com.genesis.x.service.ISystemNoticeService;
import com.genesis.x.utils.QueryUtils;
import com.genesis.x.utils.WebUtils;
import com.genesis.x.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2019/4/4 14:49
 * @Description:
 */
@RestController
@RequestMapping("/system/notice")
public class SystemNoticeController {

    @Autowired
    private ISystemNoticeService systemNoticeService;

    /**
     * 插入公告和关系表
     * @param systemNotice
     * @return
     */
    @PostMapping(value = "/addNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto insertNotices(@RequestBody SystemNotice systemNotice){
        ResultDto resultDto = systemNoticeService.insertNotices(systemNotice);
        return resultDto;
    }

    @PostMapping(value = "/countUserNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto countUserNotice(@RequestBody SystemNoticeUserRel systemNoticeUserRel){
        ResultDto resultDto = systemNoticeService.countUserNotice(systemNoticeUserRel);
        return resultDto;
    }

    @PostMapping(value = "/selectUserNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto selectUserNotice(@RequestBody QueryVo queryVo){
        SystemNoticeUserRel record = new SystemNoticeUserRel();
        QueryUtils.convert2Dto(queryVo, record);
        record.setUserId(WebUtils.getSessionUser().getId());
        ResultDto resultDto = systemNoticeService.selectUserNotice(record, queryVo.getPage());
        return resultDto;
    }

    /**
     * 标记已读
     * @param systemNoticeUserRel
     * @return
     */
    @PostMapping(value = "/readNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto readNotice(@RequestBody List<SystemNoticeUserRel> systemNoticeUserRel){
        ResultDto resultDto = systemNoticeService.readNotice(systemNoticeUserRel);
        return resultDto;
    }

    /**
     * 标记未读
     * @param systemNoticeUserRel
     * @return
     */
    @PostMapping(value = "/unReadNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto unReadNotice(List<SystemNoticeUserRel> systemNoticeUserRel){
        ResultDto resultDto = systemNoticeService.unReadNotice(systemNoticeUserRel);
        return resultDto;
    }

    /**
     * 标记删除
     * @param systemNoticeUserRel
     * @return
     */
    @PostMapping(value = "/deleteNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto deleteNotice(List<SystemNoticeUserRel> systemNoticeUserRel){
        ResultDto resultDto = systemNoticeService.deleteNotice(systemNoticeUserRel);
        return resultDto;
    }

}