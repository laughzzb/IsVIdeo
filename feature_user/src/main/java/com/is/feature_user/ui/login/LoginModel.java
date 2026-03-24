package com.is.feature_user.ui.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.is.feature_user.api.UserApiServiceProvider;
import com.is.feature_user.bean.ReqMobileLogin;
import com.is.feature_user.bean.ResLogin;
import com.is.feature_user.bean.ResSendSmsCode;
import com.is.libbase.base.BaseApplication;
import com.is.libbase.manager.ResUser;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.manager.UserManager;
import com.is.network.ApiCall;
import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResBase;

import retrofit2.Call;

/**
 * EditInfoModel 处理数据、存储数据的
 */
public class LoginModel {

    /**
     * 发送验证码
     *
     * @param mobile   手机号
     * @param callback 回调
     */
    public void sendSmsCode(String mobile, IRequestCallback<ResBase<ResBase>> callback) {

        ResSendSmsCode smsCode = new ResSendSmsCode(mobile, "mobilelogin");
        Call<ResBase<ResBase>> call = UserApiServiceProvider.getApiService().sendSmsCode(smsCode);
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResBase>>() {
            @Override
            public void onSuccess(ResBase<ResBase> result) {
                callback.onLoadFinish(result);

            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode,message);
            }
        });
    }

    /**
     * 手机号登录
     *
     * @param mobile   手机号
     * @param code     验证码
     * @param callback 回调
     */
    public void mobileLogin(String mobile, String code, IRequestCallback<ResBase<ResLogin>> callback) {

        ReqMobileLogin login = new ReqMobileLogin(mobile, code);
        Call<ResBase<ResLogin>> call = UserApiServiceProvider.getApiService().mobileLogin(login);
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResLogin>>() {
            @Override
            public void onSuccess(ResBase<ResLogin> result) {
                callback.onLoadFinish(result);

                String token = result.getData().getToken();
                //token存储放在model中，因为MODEl专门处理数据
                UserManager userManager = UserManager.getInstance();
                userManager.saveToken(token);


                //登录成功后表示同意用户协议
                SharedPreferences preferences = BaseApplication.getContext().getSharedPreferences("agreementStatus", Context.MODE_PRIVATE);
                //保存同意协议状态
                preferences.edit().putBoolean("agreement privacy",true).apply();

            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode,message);
            }
        });
    }

    /**
     * 获取用户信息
     * @param userId
     * @param callback
     */
    public void getUserInfo(String userId, IRequestCallback<ResBase<ResUser>> callback){
        Call<ResBase<ResUser>> call = UserApiServiceProvider.getApiService().getUserInfo(userId, "archives");

        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResUser>>() {
            @Override
            public void onSuccess(ResBase<ResUser> result) {

                ResUser resUser = result.getData();
                if (resUser!=null){
                    //存储用户信息
                    UserManager.getInstance().saveUserInfo(result.getData());
                    callback.onLoadFinish(result);
                }else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR,"用户信息获取失败");
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode,message);
            }
        });
    }
}

