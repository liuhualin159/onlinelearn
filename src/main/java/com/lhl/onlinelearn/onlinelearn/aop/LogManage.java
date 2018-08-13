package com.lhl.onlinelearn.onlinelearn.aop;

import java.lang.annotation.*;

/**
 * @Description: 日志注解
 * @author Liuhl
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogManage {

    public String description();
}
