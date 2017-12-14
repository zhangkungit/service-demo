package com.yryz.common.event.entity;

public class EventMessage {

    /**
     * 互动人
     */
    private String userId;

    /**
     * 互动人头像
     */
    private String headImg;

    /**
     * 互动人昵称
     */
    private String name;

    /**
     * 提示框图片
     */
    private String bodyImg;

    /**
     * 提示框内容
     */
    private String bodyTitle;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodyImg() {
        return bodyImg;
    }

    public void setBodyImg(String bodyImg) {
        this.bodyImg = bodyImg;
    }

    public String getBodyTitle() {
        return bodyTitle;
    }

    public void setBodyTitle(String bodyTitle) {
        this.bodyTitle = bodyTitle;
    }
}
