package com.x.security;

import com.alibaba.fastjson.JSON;
import com.x.config.SpringContextHolder;
import com.x.dto.ResultDto;
import com.x.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
            log.info("---------------onAuthenticationSuccess---------------------");
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            if(HttpUtils.isAjaxRequest(request)){
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getOutputStream().write(JSON.toJSONString(new ResultDto<>(HttpStatus.UNAUTHORIZED, "认证失败！")).getBytes());

            } else {
                response.sendRedirect("/login");
            }
        }
    }

    class CustomAccessDeineHandler implements AccessDeniedHandler {

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getOutputStream().write(JSON.toJSONString(new ResultDto<>(HttpStatus.FORBIDDEN, "拒绝访问！")).getBytes());
        }

    }


    @Value("${login.page}")
    private String loginPage;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

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
                .and().formLogin().successHandler(new SuccessHandler())
                //.loginPage(loginPage)
                .permitAll()
                .and().logout().permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling()
                //设置了这个会失效 http://localhost:1111/oauth/authorize?response_type=code&client_id=genesis&redirect_uri=http://baidu.com
                //.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeineHandler())
                //用户只能存在一个
                .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/login?expired")

        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomUserDetailsManager bean = SpringContextHolder.getBean(CustomUserDetailsManager.class);
        auth.authenticationProvider(customAuthenticationProvider).userDetailsService(bean);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean() ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

}