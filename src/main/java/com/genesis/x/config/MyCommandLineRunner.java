package com.genesis.x.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


/**
 * @Author: liuxing
 * @Date: 2018/11/8 17:53
 * @Description:
 */
@Slf4j
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... strings) throws Exception {
        FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping = (FrameworkEndpointHandlerMapping)applicationContext.getBean("oauth2EndpointHandlerMapping");
        frameworkEndpointHandlerMapping.getHandlerMethods().forEach((k, v) -> {
            log.info("{} - {}", k.toString(), v.getMethod().toString());
        });

        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        mapping.getHandlerMethods().forEach((k, v) -> {
            log.info("{} - {}", k.toString(), v.getMethod().toString());
        });
    }
}