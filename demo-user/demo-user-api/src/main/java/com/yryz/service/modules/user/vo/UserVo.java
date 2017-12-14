package com.yryz.service.modules.user.vo;

import java.io.Serializable;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/11/30
 * @description
 */
public class UserVo implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 平台初始密码
     */
    private String newPassword;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户真实姓名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
