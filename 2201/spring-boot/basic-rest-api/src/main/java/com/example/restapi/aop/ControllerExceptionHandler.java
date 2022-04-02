package com.example.restapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ControllerExceptionHandler {

    /*private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @Pointcut("execution(public * com.example.restapi.controller.UserController.*(..))")
    public void responseEntityHandlers() {}

    @Before("responseEntityHandlers()")
    public void beforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Before " + methodName);
    }

    *//*@AfterReturning(pointcut = "responseEntityHandlers()", returning = "response")
    public void afterController(JoinPoint joinPoint, ResponseEntity<?> response) {
        logger.info("After Returning: {}", response.getStatusCode().name());
    }

    @AfterThrowing(pointcut = "responseEntityHandlers()", throwing = "e")
    public void afterException(JoinPoint joinPoint, Exception e) {
        logger.error(e.getMessage(), e);
    }*//*

    @Around("responseEntityHandlers()")
    public Object aroundController(ProceedingJoinPoint joinPoint) {

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            if(e instanceof RuntimeException) {
                return ResponseEntity.badRequest().body(
                        e.getMessage() == null ? e.getClass().getName() : e.getMessage()
                );
            }
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }*/





}
