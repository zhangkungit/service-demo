package com.yryz.service.modules.dbsync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 *  MySQL数据同步服务，支持高可用
 *  目前支持MySQL 向Mongo同步
 */
@Service
public class DbSyncService implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DbSyncService.class);

    @Autowired
    CanalInitHandler canalInitHandler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("dbSyncService onApplicationEvent");
        canalInitHandler.initCanalStart();
    }
}
