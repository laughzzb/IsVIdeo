package com.is.feature_user.ui.settings;

import com.is.feature_user.api.UserApiService;
import com.is.feature_user.api.UserApiServiceProvider;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.manager.UserManager;
import com.is.libbase.utils.CacheUtils;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;

import retrofit2.Call;

public class SettingsModel {

    /**
     * 获取缓存大小
     * @return
     */
    public String getCacheSize(){
        String totalCacheSize = CacheUtils.getTotalCacheSize();
        return totalCacheSize;
    }

    /**
     * 清除缓存
     */
    public boolean clearCache(){
        boolean b = CacheUtils.cleanAppCache();//清除缓存
//        boolean b1 = CacheUtils.clearExternalCache();//如果需要对外部存储的数据做删除，可以自行处理外部的存储目录

        return b ;//如果返回true 表示删除成功
    }

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
            // 添加长度检查
            if (mobile != null && mobile.length() >= 8) { // 确保能调用 substring(7)
                StringBuffer buffer = new StringBuffer();
                buffer.append(mobile.substring(0,3));
                buffer.append("****");
                buffer.append(mobile.substring(7));
                return buffer.toString();
            }
        }
        return null;
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
