package com.genesis.x.service.impl;

import com.alibaba.fastjson.JSON;
import com.genesis.x.GenesisApplicationTests;
import com.genesis.x.dao.entity.SystemMenu;
import com.genesis.x.service.ISystemMenuService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: liuxing
 * @Date: 2018/12/12 15:34
 * @Description:
 */
@Ignore
public class SystemMenuImplTest extends GenesisApplicationTests {

    @Autowired
    private ISystemMenuService menuService;

    @Test
    public void selectSystemMenusTest(){
        List<SystemMenu> systemMenus = menuService.selectSystemMenus();
        System.out.println(JSON.toJSONString(systemMenus));
    }

}