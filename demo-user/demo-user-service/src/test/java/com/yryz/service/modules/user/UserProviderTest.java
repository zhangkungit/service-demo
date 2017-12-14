package com.yryz.service.modules.user;

import com.google.common.collect.Maps;
import com.yryz.common.utils.BeanMapUtils;
import com.yryz.service.modules.user.api.UserApi;
import com.yryz.service.modules.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/11/30
 * @description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class UserProviderTest {

    @Autowired
    private UserApi userProvider;

    @Test
    public void insertTest() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {

            User user = new User();
            user.setKid(0001L);
            user.setUserId("0001");
            user.setUserName("demo1");
            user.setTableName("demo_user");
            Map<String, Object> userMap = BeanMapUtils.beanToMap(user);
            String result = userProvider.insert(userMap);
            System.out.println("userProvider.insert: " + result);
            Thread.sleep(1000);
        }
    }


    @Test
    public void selectTest() throws InterruptedException {
        Map<String, Object> userMap = Maps.newHashMap();
        userMap.put("userId", "11111");
        String result = userProvider.select(userMap);
        System.out.println(result);
    }
}
