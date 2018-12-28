package com.genesis.x.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author liuxing
 */
@Slf4j
@Aspect
@Component
public class ControllerAop {

    /**
     * 排除特定类.
     */
    @Pointcut("execution(* com.genesis.x.web..*Controller.*(..))")
    public void controller() {
    }

    /**
     * 声明环绕通知.
     * @param proceedingJoinPoint 连接点
     * @return 对象
     * @throws Throwable 异常
     */
    @Around("controller()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        final String url = request.getRequestURL().toString();
        final String method = request.getMethod();
        final String queryString = request.getQueryString();
        Object obj = null;
        StopWatch watch = new StopWatch();
        try{
            watch.start();
            obj = proceedingJoinPoint.proceed();
            watch.stop();
        } finally {
            log.info("[服务执行耗时：{}毫秒] - [服务请求路径：{}] - [服务请求方式：{}] - [服务接口方法：{}] - [参数：{}]",
                    watch.getTotalTimeMillis(),
                    url + (StringUtils.hasText(queryString) ? ("?" + queryString) : ""),
                    method,
                    proceedingJoinPoint.getSignature().toString(),
                    paramToJson(proceedingJoinPoint.getArgs()));
            log.info("返回值：{}", obj != null ? JSON.toJSONString(obj) : "");
        }
        return obj;
    }

    /**
     * 解决打印request问题
     * @param objects
     * @return
     */
    private String paramToJson(Object[] objects){
        if(objects == null || objects.length == 0){
            return "";
        }
        ArrayList<Object> paramList = new ArrayList<>(objects.length);
        Arrays.asList(objects).forEach(obj -> {
            //obj.getClass().isAssignableFrom(AddAuthorizationWrapper.class) ||
            if(obj.getClass().getSimpleName().contains("MultipartFile")){

            } else {
                paramList.add(obj);
            }
        });
        return JSON.toJSONString(paramList);
    }
    
}
