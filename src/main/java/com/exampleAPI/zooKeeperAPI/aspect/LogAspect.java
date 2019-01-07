package com.exampleAPI.zooKeeperAPI.aspect;

import com.exampleAPI.zooKeeperAPI.model.LogItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

import static com.exampleAPI.zooKeeperAPI.support.JsonAndObject.LogTypeToJson;
import static com.exampleAPI.zooKeeperAPI.support.TimeFormat.currentToDate;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private Producer producer;

    @Autowired
    private LogItem logItem;

    private Logger logger = Logger.getLogger(this.getClass());

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* com.exampleAPI.zooKeeperAPI.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        setLogType(joinPoint, request);
    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) throws Throwable {

        String requestJson = responseToJson(ret);

        logger.info("LogItem : " + requestJson);

        producer.send(producer.combineMessage(logItem));
    }

    private String responseToJson(Object ret) throws JsonProcessingException {

        logItem.setResponse(ret.toString());

        return LogTypeToJson(logItem);
    }

    private void setLogType(JoinPoint joinPoint, HttpServletRequest request) {

        logItem.setDate(currentToDate());

        logItem.setParameter(Arrays.toString(joinPoint.getArgs()));

        logItem.setType(request.getMethod());

        logItem.setUrlType(request.getRequestURL().toString());
    }
}

