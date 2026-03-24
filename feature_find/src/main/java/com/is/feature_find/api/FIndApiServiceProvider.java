package com.is.feature_find.api;

import com.is.network.RetroFitProvider;

import retrofit2.Retrofit;

public class FIndApiServiceProvider {
    private  static FIndApiService mApiService;

    public static FIndApiService getApiService(){
        if (mApiService ==null){
            Retrofit retrofit = RetroFitProvider.provider();
            mApiService = retrofit.create(FIndApiService.class);
        }
        return  mApiService;
    }
}
