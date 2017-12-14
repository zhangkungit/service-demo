package com.yryz.service.modules.dbsync.config;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.lang.annotation.*;

/**
 * 介绍
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface Table {
    String value() default "";

    CanalEntry.EventType[] event() default {};
}
