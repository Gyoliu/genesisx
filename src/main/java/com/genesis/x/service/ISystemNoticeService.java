package com.genesis.x.service;

import com.genesis.x.dao.entity.SystemNotice;
import com.genesis.x.dao.entity.SystemNoticeUserRel;
import com.genesis.x.dto.Page;
import com.genesis.x.dto.ResultDto;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2019/4/2 15:40
 * @Description:
 */
public interface ISystemNoticeService {

    /**
     * 插入公告表
     * @param systemNotice
     * @return
     */
    ResultDto insertNotice(SystemNotice systemNotice);

    /**
     * 插入公告和关系表
     * @param systemNotice
     * @return
     */
    ResultDto insertNotices(SystemNotice systemNotice);

    /**
     *
     * @param systemNoticeUserRels
     * @return
     */
    ResultDto insertNoticeUserRel(List<SystemNoticeUserRel> systemNoticeUserRels);

    ResultDto countUserNotice(SystemNoticeUserRel systemNoticeUserRel);

    ResultDto selectUserNotice(SystemNoticeUserRel systemNoticeUserRel, Page page);

    /**
     * 标记已读
     * @param systemNoticeUserRel
     * @return
     */
    ResultDto readNotice(List<SystemNoticeUserRel> systemNoticeUserRel);

    /**
     * 标记未读
     * @param systemNoticeUserRel
     * @return
     */
    ResultDto unReadNotice(List<SystemNoticeUserRel> systemNoticeUserRel);

    /**
     * 标记删除
     * @param systemNoticeUserRel
     * @return
     */
    ResultDto deleteNotice(List<SystemNoticeUserRel> systemNoticeUserRel);

}