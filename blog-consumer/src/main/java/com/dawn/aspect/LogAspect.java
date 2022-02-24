package com.dawn.aspect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut(value = "execution(* com.dawn.web..*.*(..))")
    public void log() {

    }

    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog();
        requestLog.setUrl(url);
        requestLog.setIp(ip);
        requestLog.setClassMethod(classMethod);
        requestLog.setArgs(args);
        
        log.info("Request: {}", requestLog);
    }

    @After(value = "log()")
    public void doAfter() {
        log.info("------------doAfter--------------");
    }

    @AfterReturning(value = "log()", returning = "result")
    public void doAfterReturn(Object result) {
        log.info("Result: {}", result);
    }

    @Data
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
