package com.example.restapi.aop;

import com.example.restapi.exception.InvalidArgumentException;
import com.example.restapi.exception.RecordNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ControllerAop {

    private final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    @Pointcut("execution(public org.springframework.http.ResponseEntity com.example.restapi.controller..*(..))")
    public void endpointHandlerPointcut() {
        // it represents a variable for a pointcut expression
    }

    /*@Before("endpointHandlerPointcut()")
    public void beforeException(JoinPoint joinPoint) {
        logger.info("Before Execution: {}", joinPoint.getSignature().getName());
    }

    @After("endpointHandlerPointcut()")
    public void afterException(JoinPoint joinPoint) {
        logger.info("After Execution: {}", joinPoint.getSignature().getName());
    }*/

//    @Around("endpointHandlerPointcut()")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();
        Instant start = Instant.now();
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
            Duration executionTime = Duration.between(start, Instant.now());
            logger.info("{} Method Execution time {} ms", methodName, executionTime.toMillis());
        }
        return result;
    }

    private Object handleException(Throwable e) {
        if(e instanceof RecordNotFoundException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } else if(e instanceof InvalidArgumentException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        else {
            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }
}
