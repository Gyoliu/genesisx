package com.x.security;

import com.x.config.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * @Author: liuxing
 * @Date: 2018/11/12 11:07
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and().csrf().disable().anonymous()
                .and().authorizeRequests().antMatchers("/oauth/**").permitAll()
                .and().formLogin().permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                //用户只能存在一个
                .and().sessionManagement().maximumSessions(1).expiredUrl("/login?expired")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        CustomUserDetailsManager bean = SpringContextHolder.getBean(CustomUserDetailsManager.class);
        return bean;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        CustomUserDetailsManager bean = SpringContextHolder.getBean(CustomUserDetailsManager.class);
        return bean;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean() ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/hello")
                .and().ignoring().antMatchers(HttpMethod.OPTIONS);
    }

}