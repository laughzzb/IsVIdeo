package com.is.feature_piaza.api;

import com.is.network.RetroFitProvider;

import retrofit2.Retrofit;

/**
 * plaza模块中的PlazaApiService统一在这里获取，以便统一管理
 */
public class PlazaApiServiceProvider {
    private  static PlazaApiService mApiService;
    public static PlazaApiService getApiService(){
        if (mApiService ==null){
            Retrofit retrofit = RetroFitProvider.provider();
            mApiService = retrofit.create(PlazaApiService.class);
        }
        return  mApiService;
    }
}
