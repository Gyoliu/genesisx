package com.x.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

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
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> {
            PatternsRequestCondition patternsCondition = k.getPatternsCondition();
            List<String> collect = patternsCondition.getPatterns().stream().map(pattern -> pattern).collect(Collectors.toList());
            String path = String.join(",", collect);
            RequestMethodsRequestCondition methodsCondition = k.getMethodsCondition();
            List<String> types = methodsCondition.getMethods().stream().map(requestMethod -> requestMethod.toString()).collect(Collectors.toList());
            String type = String.join(",", types);
            String className = v.getMethod().getDeclaringClass().getName();
            log.info("[classname:{}, path:{}, type:{}]",className, path, type);
        });
    }
}