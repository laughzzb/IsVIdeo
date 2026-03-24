package com.is.feature_user.api;

import com.is.network.RetroFitProvider;

import retrofit2.Retrofit;

/**
 * User模块中的UserApiService统一在这里获取，以便统一管理
 */
public class UserApiServiceProvider {
    private  static UserApiService mApiService;
    public static UserApiService getApiService(){
        if (mApiService ==null){
            Retrofit retrofit = RetroFitProvider.provider();
            mApiService = retrofit.create(UserApiService.class);
        }
        return  mApiService;
    }
}
