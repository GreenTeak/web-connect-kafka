package com.exampleAPI.zooKeeperAPI.aspect;

import com.exampleAPI.zooKeeperAPI.model.LogType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

@Aspect
@Component
public class LogAspect {
    @Autowired
    private Producer producer;

    private Logger logger = Logger.getLogger(this.getClass());
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public ThreadLocal<Long> startTime = new ThreadLocal<>();
    private LogType logType = new LogType();

    @Pointcut("execution(* com.exampleAPI.zooKeeperAPI.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logType.setData(String.valueOf(System.currentTimeMillis()));
        logType.setParmater(Arrays.toString(joinPoint.getArgs()));
        logType.setType(request.getMethod());
        logType.setUrl(request.getRequestURL().toString());
    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) throws Throwable {
        logType.setReponse(ret.toString());
        String requestJson = ow.writeValueAsString(logType);
        logger.info("LogType : " + requestJson);
        producer.send(requestJson);
    }
}

