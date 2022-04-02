package com.example.restapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Aspect
@Component
public class ControllerAop {

    private final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    @Pointcut("execution(public org.springframework.http.ResponseEntity com.example.restapi.controller..*(..))")
    public void endpointHandlerPointcut() {
        // it represents a variable for a pointcut expression
    }

    @Before("endpointHandlerPointcut()")
    public void beforeException(JoinPoint joinPoint) {
        logger.info("Before Execution: {}", joinPoint.getSignature().getName());
    }

    @After("endpointHandlerPointcut()")
    public void afterException(JoinPoint joinPoint) {
        logger.info("After Execution: {}", joinPoint.getSignature().getName());
    }

    @Around("endpointHandlerPointcut()")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        logger.info("{} Starting Time: {}", methodName, LocalDateTime.now());
        Object result = null;
        try {
            result = joinPoint.proceed();
            logger.info("Got result from {}", methodName);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            result = this.handleException(e);
        } finally {
            logger.info("{} Exiting Time: {}", methodName, LocalDateTime.now());
        }
        return result;
    }

    private Object handleException(Throwable e) {
        if(e instanceof RuntimeException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } else {
            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }
}
