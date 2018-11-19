package com.x.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: liuxing
 * @Date: 2018/11/12 11:07
 * @Description:
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${login.page}")
    private String loginPage;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and().csrf().disable().anonymous()
                .and().formLogin().successHandler(new AuthenticationSuccessHandler())
                //.loginPage(loginPage)
                .permitAll()
                .and().logout().permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                //用户只能存在一个
                .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/login?expired")

        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider).userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
    }

}