package com.yryz.service.user.web;

import com.google.common.collect.Maps;
import com.yryz.common.web.BaseController;
import com.yryz.service.modules.user.api.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("services/app/v1/user")
public class UserH5Controller extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserH5Controller.class);

    @Autowired
    private UserApi userProvider;

    /**
     * 获取用户信息
     *
     * @param userId 目标用户的id
     * @param custId 目标用户的custId
     * @return
     */
    @RequestMapping(value = "info", method = {RequestMethod.GET})
    @ResponseBody
    public String getUserInfo(Long userId) {
        Map<String, Object> userMap = Maps.newHashMap();
        userMap.put("userId", userId.toString());
        String result = userProvider.select(userMap);
        /*User user = new User();
        user.setUserId(userId.toString());
        user.setUserName("kk");*/
//        logger.info("getUserInfo result: {}", JSON.toJSONString(user));
        return result;

    }


}
