package com.x.config;

import com.x.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cors.host:http://localhost:8080}")
    private String host;
    /**
     * 设置跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .maxAge(3600L)
                .allowedOrigins(host)
                .allowedHeaders("*")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.OPTIONS.name())
                .allowCredentials(true)
        ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }
}
