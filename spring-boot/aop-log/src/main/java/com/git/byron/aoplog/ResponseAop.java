package com.git.byron.aoplog;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.aoplog.annotation
 * @ClassName: LogAnnotation
 * @Description:
 * @Date: 2019/8/8 14:53
 * @Version: 1.0
 */
@Aspect
@Component
@Log4j2
public class ResponseAop {

    //直接在这里定义基本类型会有同步问题，所以我们定义一个ThreadLocal对象来记录消耗的时间
    ThreadLocal<Long> threadLocal = new ThreadLocal<>();


    /**
     * 切点
     * */
    @Pointcut("@annotation(com.git.byron.aoplog.annotation.LogAnnotation)")
    private void httpResponse(){}

    /**
     * 切入开始执行
     * */
    @Before("httpResponse()")
    private void doBefore(JoinPoint joinPoint){

        //开始计时
        threadLocal.set(System.currentTimeMillis());
        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //打印请求信息
        log.info("接口路径：{}" , request.getRequestURL().toString());
        log.info("浏览器：{}", userAgent.getBrowser().toString());
        log.info("浏览器版本：{}",userAgent.getBrowserVersion());
        log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
        log.info("IP : {}" , request.getRemoteAddr());
        log.info("请求类型：{}", request.getMethod());
        log.info("类方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : {} " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 切入方法执行完后执行
     * */
    @AfterReturning(returning = "ret",pointcut = "httpResponse()")
    private void doAfterReturning(Object ret){
        log.info("方法执行时间 : {}毫秒: ",(System.currentTimeMillis() - threadLocal.get()));
        log.info("方法返回值: {}",ret);
    }
}
