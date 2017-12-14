package com.yryz.service.modules.user.service.impl;

import com.google.common.collect.Maps;
import com.yryz.common.utils.BeanMapUtils;
import com.yryz.service.modules.user.dao.persistence.UserDao;
import com.yryz.service.modules.user.entity.User;
import com.yryz.service.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/11/30
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public String insert(User user) {
        Map<String, Object> userMap = BeanMapUtils.beanToMap(user);
        userDao.insertByPrimaryKeySelective(userMap);
        return "1";
    }

    @Override
    public String select(User user) {
        return userDao.selectByUserId(user).toString();
    }

    @Override
    public String update(User user) {
        return userDao.updateByUserId(user).toString();
    }

    @Override
    public String delete(User user) {
        return userDao.deleteByUserId(user).toString();
    }
}
