package com.yryz.service.modules.user.provider;

import com.yryz.common.utils.BeanMapUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.service.modules.id.api.IdAPI;
import com.yryz.service.modules.user.api.UserApi;
import com.yryz.service.modules.user.entity.User;
import com.yryz.service.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/11/30
 * @description
 */
@Service("userProvider")
public class UserProvider implements UserApi {
    private static final Logger logger = LoggerFactory.getLogger(UserProvider.class);

    @Autowired
    private UserService userService;


    @Autowired
    IdAPI idAPIImpl;

//    @Autowired
//    private KafkaTemplate kafkaTemplate;

    /**
     * 新增User
     *
     * @param userMap
     * @return
     */
    @Override
    public String insert(Map<String, Object> userMap) {
        logger.info("UserProvider.insert request: {}", userMap);
        logger.warn("UserProvider.insert request: {}", userMap);
//        kafkaTemplate.send("sgt", "UserProvider.insert request: " + userMap);
        User user = new User();
        BeanMapUtils.mapToBean(userMap, user);
        return userService.insert(user);
    }

    /**
     * 查询User信息
     *
     * @param userMap id 信息
     * @return
     */
    @Override
    public String select(Map<String, Object> userMap) {
        User user = new User();
        user.setUserName("kkkkk");
        user.setUserId((String) userMap.get("userId"));
//        BeanMapUtils.mapToBean(userMap, user);
        String orderId = idAPIImpl.getId("qshop_order");
        user.setKid(Long.valueOf(orderId));
        return GsonUtils.parseJson(user);
    }

    /**
     * User更新
     *
     * @param userMap
     * @return
     */
    @Override
    public String update(Map<String, Object> userMap) {
        User user = new User();
        BeanMapUtils.mapToBean(userMap, user);
        return userService.update(user);
    }

    /**
     * User更新
     *
     * @param userMap
     * @return
     */
    @Override
    public String delete(Map<String, Object> userMap) {
        User user = new User();
        BeanMapUtils.mapToBean(userMap, user);
        return userService.delete(user);
    }
}
