package com.x.service.impl;

import com.alibaba.fastjson.JSON;
import com.x.GenesisApplicationTests;
import com.x.dao.entity.SystemMenu;
import com.x.service.ISystemMenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/12 15:34
 * @Description:
 */
public class SystemMenuImplTest extends GenesisApplicationTests {

    @Autowired
    private ISystemMenuService menuService;

    @Test
    public void selectSystemMenusTest(){
        List<SystemMenu> systemMenus = menuService.selectSystemMenus();
        System.out.println(JSON.toJSONString(systemMenus));
    }

}