package com.yryz.service.modules.user.dao.persistence;

import com.yryz.service.modules.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/11/30
 * @description
 */
@Repository
public interface UserDao {

    void insertByPrimaryKeySelective(Map<String, Object> user);

    User selectByUserId(User user);

    Integer updateByUserId(User user);

    Integer deleteByUserId(User user);
}
