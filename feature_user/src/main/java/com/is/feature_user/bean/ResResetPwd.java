package com.is.feature_user.bean;

public class ResResetPwd {
    private String newpassword;
    private String mobile;
    private String captcha;
    private String type;

    public ResResetPwd(String newpassword,String mobile ,String captcha) {
        this.newpassword = newpassword;
        this.mobile = mobile;
        this.captcha = captcha;
        this.type = "mobile";


    }
}
