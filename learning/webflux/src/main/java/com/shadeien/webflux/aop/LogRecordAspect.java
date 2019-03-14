package com.shadeien.webflux.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

/**
 * Created by shade on 2017/11/15.
 */
@Aspect   //定义一个切面
@Configuration
public class LogRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    @Around("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object o : args) {
            logger.info("{}", o);
            if (o instanceof ServerWebExchange) {
                ServerWebExchange serverWebExchange = (ServerWebExchange) o;
                serverWebExchange.getAttributes().put("a", "b");
            }
        }

        return pjp.proceed();
    }

}
