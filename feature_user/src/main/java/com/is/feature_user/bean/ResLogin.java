package com.is.feature_user.bean;

public class ResLogin {
    private String token;// 用户身份标识
    private int id;//当前登录的用户id

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "ResLogin{" +
                "token='" + token + '\'' +
                ", id=" + id +
                '}';
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
