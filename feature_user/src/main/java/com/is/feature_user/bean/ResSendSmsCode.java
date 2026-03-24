package com.is.feature_user.bean;

/**
 * 发送验证码请求的请求体
 */
public class ResSendSmsCode {

    private String mobile;//手机号
    private String event;//事件名称  一般用mobileLogin 就可以   resetpwd表示重置密码

    public ResSendSmsCode(String mobile, String event) {
        this.mobile = mobile;
        this.event = event;
    }
}
