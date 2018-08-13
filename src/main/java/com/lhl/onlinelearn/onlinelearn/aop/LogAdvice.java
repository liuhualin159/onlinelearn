package com.lhl.onlinelearn.onlinelearn.aop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LogAdvice {

    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Before("within(com.lhl.onlinelearn..*) && @annotation(logManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LogManage logManage) {
        logger.info("执行 " + logManage.description() + " 开始");
        logger.info(joinPoint.getSignature().toString());
        logger.info(parseParames(joinPoint.getArgs()));
    }

    @AfterReturning("within(com.lhl.onlinelearn..*) && @annotation(logManage)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LogManage logManage) {
        logger.info("执行 " + logManage.description() + " 结束");
    }

    @AfterThrowing(pointcut = "within(com.lhl.onlinelearn..*) && @annotation(logManage)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LogManage logManage, Exception ex) {
        logger.error("执行 " + logManage.description() + " 异常", ex);
    }

    private String parseParames(Object[] parames) {
        if (null == parames || parames.length <= 0 || parames.length >1024) {
            return "";
        }
        StringBuffer param = new StringBuffer("传入参数[{}] ");
        for (Object obj : parames) {
            param.append(ToStringBuilder.reflectionToString(obj)).append("  ");
        }
        return param.toString();
    }

}
