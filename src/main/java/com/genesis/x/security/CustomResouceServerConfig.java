package com.genesis.x.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * @Author: liuxing
 * @Date: 2018/11/12 10:29
 * @Description:
 */
@Slf4j
@Order(2)
@Configuration
@EnableResourceServer
public class CustomResouceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        OAuth2AuthenticationProcessingFilter f = new OAuth2AuthenticationProcessingFilter();
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setExceptionTranslator(new CustomResponseExceptionTranslator());
        f.setAuthenticationEntryPoint(oAuth2AuthenticationEntryPoint);
        OAuth2AuthenticationManager o = new OAuth2AuthenticationManager();
        DefaultTokenServices dt = new DefaultTokenServices();
        dt.setTokenStore(tokenStore);
        o.setTokenServices(dt);
        f.setAuthenticationManager(o);
        http.addFilterBefore(f, AbstractPreAuthenticatedProcessingFilter.class);

        http.requestMatchers()
                .antMatchers("/api/**")
                .antMatchers("/user/**")
                .antMatchers("/role/**")
                .antMatchers("/resource/**")
                .antMatchers("/system/**")
                .antMatchers("/blog/**")
                .antMatchers("/error")
                .and().httpBasic()
                .and().csrf().disable().anonymous()
                .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().authorizeRequests().antMatchers("/").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler()).authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(AuthorizationServerConfiguration.resourceId).stateless(true);
    }
}