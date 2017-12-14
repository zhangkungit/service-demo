package com.yryz.service.modules.dbsync.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 介绍
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public  @interface Schema {
    String value() default "";
}
