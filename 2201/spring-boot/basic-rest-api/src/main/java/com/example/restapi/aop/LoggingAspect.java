package com.example.restapi.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Aspect
@Component
public class LoggingAspect {

    private final ObjectMapper jacksonMapper;
    private final Logger log;

    public LoggingAspect() {
        jacksonMapper = new ObjectMapper();
        jacksonMapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, true);
        jacksonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        log = LoggerFactory.getLogger(this.getClass());
    }


    @Pointcut("@annotation(EnableLogging)")
    public void enableLoggingPointcut() {
        /* Matches Methods with @EnableLogging annotation */
    }

    @Around(value = "enableLoggingPointcut()")
    public Object performanceLoggingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        traceLog(joinPoint);
        return this.proceedAndLogPerformance(joinPoint);
    }

    private void traceLog(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder("\n-----------------------\n")
                .append(format("Class: %s%n", joinPoint.getSignature().getDeclaringTypeName()))
                .append(format("Method: %s%n", joinPoint.getSignature().getName()));

        AtomicInteger count = new AtomicInteger();
        Arrays.stream(joinPoint.getArgs())
                .forEach(argsAppender(builder, count));

        builder.append("-------------------------");
        log.info(builder.toString());
    }

    private Consumer<Object> argsAppender(StringBuilder builder, AtomicInteger count) {
        return o -> {
            try {
                String json = jacksonMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(o);
                builder.append(
                        format("Args [%s]: %s%n",
                                count.incrementAndGet(),
                                json));
            } catch (JsonProcessingException e) {
                log.info("Error occurred while parsing Args");
                log.error(e.getMessage());
            }
        };
    }

    private Object proceedAndLogPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long entryTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        // executing actual method
        long executionTime = System.currentTimeMillis() - entryTime;

        String performance = format("Executed %s - %s taken %d second",
                        joinPoint.getTarget().getClass().getName(),
                        joinPoint.getSignature().getName(),
                        (executionTime/1000) );
        log.info(performance);
        return result;
    }
}
