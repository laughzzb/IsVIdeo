package com.is.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitProvider {
    private static final String BASE_URL = "https://titok.fzqq.fun/";
    private static Retrofit mRetrofit;

    /**
     * 单例设计模式
     * @return
     */
    public static Retrofit provider(){
        if (mRetrofit ==null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkhttpClientProvider.provide())
                    .addConverterFactory(GsonConverterFactory.create())// 配置 Gson 转换器
                    .build();
        }
        return mRetrofit;
    }
}
