package com.is.feature_user.ui.account;

import com.is.libbase.manager.UserManager;

public class AccountModel {
    public boolean isLogin(){
        return UserManager.getInstance().isLogin();//是否登录
    }

    /**
     * 如果未登录，返回空
     * @return
     */
    public String getMobile() {
        if (isLogin()){
            String mobile = UserManager.getInstance().getUSerInfo().getUser().getUsername();
            //把userName中间的4位替换成****
            StringBuffer buffer = new StringBuffer();
            buffer.append(mobile.substring(0,3));
            buffer.append("****");
            buffer.append(mobile.substring(7));
            String string = buffer.toString();
            return string;//返回手机号，userName就是手机号
        }
        return null;
    }
}
