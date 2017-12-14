package com.yryz.service.modules.dbsync.config.canal;


import java.util.List;

/**
 * 介绍
 *
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
//@ConfigurationProperties(prefix = "canal")
public class CanalProperties {

    private String zkServers;//zookeeper 地址
    private List<String> destination;//监听instance列表

    public CanalProperties() {
    }

    public String getZkServers() {
        return zkServers;
    }

    public void setZkServers(String zkServers) {
        this.zkServers = zkServers;
    }

    public List<String> getDestination() {
        return destination;
    }

    public void setDestination(List<String> destination) {
        this.destination = destination;
    }
}
