package com.yryz.service.modules.user.api;

import java.util.Map;

/**
 * @author
 * @ClassName: UserApi
 * @Description: UserApi
 * @date 2017-09-01 11:40:43
 */
public interface UserApi {

    /**
     * 新增User
     *
     * @param userMap
     * @return
     */
    String insert(Map<String, Object> userMap);

    /**
     * 查询User信息
     *
     * @param userMap id 信息
     * @return
     */
    String select(Map<String, Object> userMap);


    /**
     * User更新
     *
     * @param userMap
     * @return
     */
    String update(Map<String, Object> userMap);

    /**
     * User更新
     *
     * @param userMap
     * @return
     */
    String delete(Map<String, Object> userMap);

}
