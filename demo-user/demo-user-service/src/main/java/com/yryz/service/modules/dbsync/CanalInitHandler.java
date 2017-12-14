package com.yryz.service.modules.dbsync;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.yryz.common.guava.GuavaUtils;
import com.yryz.service.modules.dbsync.cancal.CanalClient;
import com.yryz.service.modules.dbsync.config.canal.CanalProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化订阅集群模式canal
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
@Service
public class CanalInitHandler {
    protected final static Logger logger = LoggerFactory.getLogger(CanalInitHandler.class);

    @Value("${canal.zkServers}")
    private String canalZkServers;

    @Value("${canal.destination}")
    private String canalDestination;

    public void initCanalStart() {
        List<String> destinations = GuavaUtils.DOT_SPLITTER.splitToList(canalDestination);
        final List<CanalClient> canalClientList = new ArrayList<>();
        if (destinations != null && destinations.size() > 0) {
            for (String destination : destinations) {
                // 基于zookeeper动态获取canal server的地址，建立链接，其中一台server发生crash，可以支持failover
                CanalConnector connector = CanalConnectors.newClusterConnector(canalZkServers, destination, "", "");
                CanalClient client = new CanalClient(destination, connector);
                canalClientList.add(client);
                //CanalClient开始
                client.start();
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    logger.info("## stop the canal client");
                    for (CanalClient canalClient : canalClientList) {
                        //CanalClient结束
                        canalClient.stop();
                    }
                } catch (Throwable e) {
                    logger.warn("##something goes wrong when stopping canal:", e);
                } finally {
                    logger.info("## canal client is down.");
                }
            }

        });
    }
}
