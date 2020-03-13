package com.genesis.x.utils;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;

/**
 * @Author: liuxing
 * @Date: 2020/2/25 15:05
 * @Description:
 */
public class TemplateEngineUtil {

    public static String parseTemplate(String content, Map<String, Object> data){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        //IDialect iDialect = new SpringStandardDialect();
        //springTemplateEngine.setDialect(iDialect);
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setCacheable(true);
        stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
        // AbstractTemplateResolver
        springTemplateEngine.setTemplateResolver(stringTemplateResolver);
        Context context = new Context();
        context.setVariables(data);
        String result = springTemplateEngine.process(content, context);
        return result;
    }

}