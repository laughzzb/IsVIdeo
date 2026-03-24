package com.is.feature_home.api;

import com.is.network.RetroFitProvider;

import retrofit2.Retrofit;

public class HoneApiServiceProvider {
    private  static HomeApiService mApiService;

    public static HomeApiService getApiService(){
        if (mApiService ==null){
            Retrofit retrofit = RetroFitProvider.provider();
            mApiService = retrofit.create(HomeApiService.class);
        }
        return  mApiService;
    }
}
