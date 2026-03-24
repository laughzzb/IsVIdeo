package com.is.feature_user.ui.resetpwd;

import com.is.feature_user.api.UserApiServiceProvider;
import com.is.feature_user.bean.ReqMobileLogin;
import com.is.feature_user.bean.ResLogin;
import com.is.feature_user.bean.ResResetPwd;
import com.is.feature_user.bean.ResSendSmsCode;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.manager.ResUser;
import com.is.libbase.manager.UserManager;
import com.is.network.ApiCall;
import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResBase;

import retrofit2.Call;

public class ResetpwdModel {

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


    /**
     * 发送验证码
     */
    public void sendSmsCode( IRequestCallback<ResBase<ResBase>> callback) {
        //获取手机号
        String mobile = UserManager.getInstance().getUSerInfo().getUser().getUsername();
        ResSendSmsCode smsCode = new ResSendSmsCode(mobile, "resetpwd");
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
     * 重置密码
     * @param password
     * @param code
     * @param callback
     */
    public void resetPassword(String password,String code,IRequestCallback<ResBase<ResBase>> callback){
        String token = UserManager.getInstance().getToken();
        //获取手机号
        String mobile = UserManager.getInstance().getUSerInfo().getUser().getUsername();
        ResResetPwd pwd = new ResResetPwd(password,mobile,code);
        Call<ResBase<ResBase>> call = UserApiServiceProvider.getApiService().resetPassword(token, pwd);
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
            }
            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode,message);
            }
        });
    }

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
