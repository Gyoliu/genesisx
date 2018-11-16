package com.x.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @Author: liuxing
 * @Date: 2018/11/16 17:47
 * @Description: 开启enableHttpSessionEventPublisher 支持SessionRegistry
 */
@Slf4j
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected boolean enableHttpSessionEventPublisher() {
        log.info("enableHttpSessionEventPublisher");
        return true;
    }
}