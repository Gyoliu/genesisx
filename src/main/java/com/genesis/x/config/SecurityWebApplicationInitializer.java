package com.genesis.x.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @Author: liuxing
 * @Date: 2018/11/16 17:47
 * @Description: 开启enableHttpSessionEventPublisher 支持SessionRegistry
 */
@Slf4j
@Configuration
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected boolean enableHttpSessionEventPublisher() {
        log.info("enableHttpSessionEventPublisher");
        return true;
    }
}