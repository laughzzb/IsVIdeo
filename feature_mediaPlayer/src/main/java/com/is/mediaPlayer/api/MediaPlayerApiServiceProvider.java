package com.is.mediaPlayer.api;

import com.is.network.RetroFitProvider;

import retrofit2.Retrofit;

public class MediaPlayerApiServiceProvider {
    private  static MediaPlayerService mApiService;

    public static MediaPlayerService getApiService(){
        if (mApiService ==null){
            Retrofit retrofit = RetroFitProvider.provider();
            mApiService = retrofit.create(MediaPlayerService.class);
        }
        return  mApiService;
    }
}
