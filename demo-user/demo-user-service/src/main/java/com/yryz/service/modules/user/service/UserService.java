package com.yryz.service.modules.user.service;

import com.yryz.service.modules.user.entity.User;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/11/30
 * @description
 */
public interface UserService {

    String insert(User user);

    String select(User user);

    String update(User user);

    String delete(User user);
}
