package com.yryz.service.modules.id2.service;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/8/14
 * @description
 */
public interface IdService {
    /**
     * 根据type获取id
     * @param type
     * @return
     */
    Long getId(String type);

    /**
     * 返回String类型Id
     * @return
     */
    String getId();

}
