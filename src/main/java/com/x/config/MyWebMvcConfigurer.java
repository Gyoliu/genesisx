package com.x.config;

import com.x.security.WebSecurityConfig;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName:MyWebMvcConfigurer
 * Description:
 *
 * @Author Gyo
 * @Date 2018/11/17 13:19
 */
@Configuration
@AutoConfigureBefore(WebSecurityConfig.class)
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    /**
     * 设置跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .maxAge(3600L)
                .allowedOrigins("http://localhost:8080")
                .allowedHeaders("*")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.OPTIONS.name())
        ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }
}
