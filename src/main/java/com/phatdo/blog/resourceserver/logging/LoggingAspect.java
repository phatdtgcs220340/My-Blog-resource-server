package com.phatdo.blog.resourceserver.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public * com.phatdo.blog.resourceserver.services.*.*(..))")
    public void publicMethodsFromLoggingPackage() {
    }

    @Around(value = "publicMethodsFromLoggingPackage()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.debug(">> {}() - {}", methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
        log.debug("<< {}() - {}", methodName, result);
        return result;
    }
}
