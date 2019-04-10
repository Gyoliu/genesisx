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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto insertNotices(@RequestBody SystemNotice systemNotice){
        ResultDto resultDto = systemNoticeService.insertNotices(systemNotice);
        return resultDto;
    }

    @PostMapping(value = "/countUserNotice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto countUserNotice(@RequestBody SystemNoticeUserRel systemNoticeUserRel){
        if(StringUtils.isEmpty(systemNoticeUserRel.getUserId())){
            systemNoticeUserRel.setUserId(WebUtils.getSessionUser().getId());
        }
        if(systemNoticeUserRel.getStatus() == null){
            systemNoticeUserRel.setStatus(0);
        }
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

    @GetMapping(value = "/content", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto selectNoticeContent(@RequestParam("id") Integer id){
        ResultDto resultDto = systemNoticeService.selectNoticeById(id);
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