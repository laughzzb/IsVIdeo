package com.is.feature_find.api;

import com.is.feature_find.bean.ResCategoryDetail;
import com.is.feature_find.bean.ResFind;
import com.is.feature_find.bean.ResFindTopic;
import com.is.feature_find.bean.ResThemeData;
import com.is.feature_find.bean.ResTopic;
import com.is.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 这里存放find模块的api
 */
public interface FIndApiService {


    /**
     * 获取分类详情数据
     *
     * @return 服务端返回的数据类型
     */
    @POST("addons/cms/api.eye/category_detail")
    Call<ResBase<ResCategoryDetail>> getCategoryDetail(@Query("channel_id") int id);

    /**
     * 发现页数据加载
     *
     * @return
     */
    @GET("addons/cms/api.eye/find")
    Call<ResBase<ResFind>> getFindData();

    /**
     * 获取主题播单数据
     * 不用传任何参数
     * @return
     */
    @GET("addons/cms/api.eye/anchor")
    Call<ResBase<List<ResThemeData>>> getAnchor();

    /**
     * 获取话题页的数据
     * @return
     */
    @GET("addons/cms/api.eye/topic")
    Call<ResBase<List<ResTopic>>> getTopic();


}
