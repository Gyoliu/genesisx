package com.x.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static com.x.security.AuthorizationServerConfiguration.resourceId;

/**
 * @Author: liuxing
 * @Date: 2018/11/12 10:29
 * @Description:
 */
@Slf4j
@Configuration
@EnableResourceServer
public class CustomResouceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/api/**")
                .requestMatchers().antMatchers("/user/**")
                .requestMatchers().antMatchers("/role/**")
                .requestMatchers().antMatchers("/resource/**")
                .requestMatchers().antMatchers("/system/**")
                .and().authorizeRequests().antMatchers("/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler()).authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId).stateless(true);
    }
}