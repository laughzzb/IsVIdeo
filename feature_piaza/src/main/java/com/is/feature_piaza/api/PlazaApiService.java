package com.is.feature_piaza.api;
import com.is.feature_piaza.bean.ResPlaza;
import com.is.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlazaApiService {
    /**
     * 广场首页数据
     * @return 服务器返回的数据类型
     */
    @GET("addons/cms/api.eye/square")//指定请求方式，并定义了 URL 的路径
    Call<ResBase<List<ResPlaza>>> getPlaza();



}
