package com.is.feature_user.ui.user;

import com.is.feature_user.api.UserApiServiceProvider;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.manager.ResUser;
import com.is.libbase.manager.UserManager;
import com.is.network.ApiCall;
import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResBase;

import retrofit2.Call;

public class UserModel {

    public boolean isLogin(){
        return UserManager.getInstance().isLogin();//是否登录
    }

    /**
     * 获取用户信息
     * @param callback
     */
    public void loadUserInfo(ILoadUSerInfoCallback callback) {
        if (isLogin()){
            //获取到用户信息
            ResUser uSerInfo = UserManager.getInstance().getUSerInfo();
            if (uSerInfo != null){
                callback.onLoadSuccess(uSerInfo);
            }else {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN,"未登录");
            }
        }else {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN,"未登录");
        }
    }


    /**
     * 退出登录
     */
    public void logout(IRequestCallback<ResBase<ResBase>> callback) {
        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResBase>> call = UserApiServiceProvider.getApiService().logout(token);
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResBase>>() {
            @Override
            public void onSuccess(ResBase<ResBase> result) {
                if (result.getCode() ==1){
                    //清除本地已登录用户数据
                    UserManager.getInstance().logout();
                    callback.onLoadFinish(result);
                }
            }
            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }
}
