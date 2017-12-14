package com.yryz.common.constant;

public enum ExceptionEnum {

    SysException("1001", "服务器开小差了，请稍候再试！", "系统异常"),
    ValidateException("2000", "服务器开小差了，请稍候再试！", "数据验证失败！"),
    LockException("3000", "服务器开小差了，请稍候再试！", "分布式锁异常"),
    BusiException("4000", "服务器开小差了，请稍候再试！", "业务逻辑异常"),
    COMMENT_SOURCE_NOTEXIST_EXCEPTION("4001","回复的评论不存在或者已经删除！","回复的评论不存在或者已经删除"),
    COMMENT_INFOID_TOPID_EMPTY("4002", "服务器开小差了，请稍候再试！", "资源ID与父评论ID不能同时为空"),
    INFORM_REPEAT_USERID("4003", "您已经举报过此资源", "您已经举报过此资源"),
    INFORM_UNPROCESSED("4004", "此举报已处理", "此举报已处理"),
    FAVORITE_IS_NULL("4005", "服务器开小差了，请稍候再试！", "收藏数据内容不能为空"),
    FAVORITE_NOT_EXIST("4006", "此收藏记录不存在或者已删除", "此收藏记录不存在或者已删除"),

    Exception("1000", "服务器开小差了，请稍候再试！", "服务器内部错误，未知异常！");

    private String code;

    private String msg;

    private String errorMsg;

    ExceptionEnum(String code, String msg, String errorMsg) {
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
