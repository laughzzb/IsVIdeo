package com.is.feature_user.ui.user;

import com.is.libbase.manager.ResUser;

public interface ILoadUSerInfoCallback {
    /**
     * 用户信息加载成功
     * @param user
     */
    void onLoadSuccess(ResUser user);

    /**
     * 加载失败
     * @param errorCode
     * @param message
     */
    void onLoadFail(int errorCode,String message);
}
