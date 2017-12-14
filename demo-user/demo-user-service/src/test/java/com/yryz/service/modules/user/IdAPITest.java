package com.yryz.service.modules.user;

import com.google.common.collect.Sets;
import com.yryz.service.modules.id.api.IdAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;


/**
 * IdAPIImpl Tester.
 *
 * @author zhangkun
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class IdAPITest {


//    @Autowired
//    IdAPI idAPI;

    /*@Test
    public void getIdTest() {
        Set<String> stringSet = Sets.newHashSet();
        for (int i = 0; i < 18; i++) {
            String orderId = idAPI.getId("qshop_333");
            if (stringSet.contains(orderId)) {
                System.out.println("=====contains=======");
            }
            stringSet.add(orderId);
            System.out.println("getIdTest=== " + orderId);
        }
        System.out.println("get stringSet size: " + stringSet.size());
    }*/

}
