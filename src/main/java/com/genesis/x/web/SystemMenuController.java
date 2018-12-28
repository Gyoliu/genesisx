package com.genesis.x.web;

import com.genesis.x.dto.ResultDto;
import com.genesis.x.dao.entity.SystemMenu;
import com.genesis.x.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/12 16:35
 * @Description:
 */
@RestController
@RequestMapping("/system")
public class SystemMenuController {

    @Autowired
    private ISystemMenuService menuService;

    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public ResultDto selectSystemMenus() {
        List<SystemMenu> systemMenus = menuService.selectSystemMenus();
        return new ResultDto(systemMenus);
    }

}