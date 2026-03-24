package com.is.mediaPlayer.api;

import com.is.data_video.bean.ReqComment;
import com.is.data_video.bean.ReqDelectComment;
import com.is.data_video.bean.ReqVideoOperation;
import com.is.data_video.bean.ResCategoryVideoDetail;
import com.is.data_video.bean.ResComment;
import com.is.data_video.bean.ResLike;
import com.is.data_video.bean.ResSendComment;
import com.is.data_video.bean.ResVideo;
import com.is.data_video.bean.ResVideoDetail;
import com.is.network.bean.ResBase;
import com.is.network.bean.ResList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MediaPlayerService {
    @GET("addons/cms/api.eye/daily")
//指定请求方式，并定义了 URL 的路径
    Call<ResBase<ResList<ResVideo>>> getDAily(@Query("page") int page, @Query("limit") int limit);//@Path是一个动态的占位符，可以把user变量的值替换到{user}    @GET("addons/cms/api.eye/daily")//指定请求方式，并定义了 URL 的路径

    @GET("addons/cms/api.eye/recommend")
    Call<ResBase<ResList<ResVideo>>> getRecommend(@Query("page") int page, @Query("limit") int limit);//@Path是一个动态的占位符，可以把user变量的值替换到{user}

    /**
     * 获取视频详情
     *
     * @param id    视频id
     * @param token 用户token
     */
    @GET("addons/cms/api.archives/detail")
    Call<ResBase<ResVideoDetail>> getVideoDetail(@Header("token") String token, @Query("id") int id);

    /**
     * 文章点赞
     *
     * @param token
     * @param like  文章的id
     * @return
     */
    @POST("addons/cms/api.archives/vote")
    Call<ResLike> requestLike(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 取消点赞
     *
     * @param token
     * @param like  文章id
     * @return
     */
    @POST("addons/cms/api.archives/vote_del")
    Call<ResLike> requestCancelLike(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 收藏
     *
     * @param token
     * @param like
     * @return
     */
    @POST("addons/cms/api.collection/create")
    Call<ResLike> requestCollection(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 取消收藏
     *
     * @param token
     * @param like
     * @return
     */
    @POST("addons/cms/api.collection/delete")
    Call<ResLike> requestCancelCollection(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 发送评论
     *
     * @param token
     * @param operation 需要传content，aid
     * @return
     */
    @POST("addons/cms/api.comment/post")
    Call<ResBase<ResSendComment>> sendComment(@Header("token") String token, @Body ReqComment operation);

    /**
     * 评论列表
     * @param token
     * @param id 视频id
     * @param page 分页数
     * @return
     */
    @GET("addons/cms/api.comment/index")
    Call<ResBase<ResList<ResComment>>> requestCommentList(@Header("token") String token, @Query("aid")int  id,@Query("page") int page);

    /**
     *
     * @param token
     * @param comment 评论id
     * @return
     */
    @POST("addons/cms/api.comment/delete")
    Call<ResBase<ReqComment>> DeleteComment(@Header("token") String token, @Body ReqDelectComment comment);

    /**
     * 获取分类详情列表
     *
     * @return 服务端返回的数据类型
     */
    @GET("addons/cms/api.eye/category_list")
    Call<ResBase<ResList<ResCategoryVideoDetail>>> getCategoryLists(
            @Header("token") String token,
            @Query("type") int type,
            @Query("channel_id") int id,
            @Query("page") int page,
            @Query("limit") int limit
    );

    /**
     * 搜索
     * @param keyword 关键词
     * @return
     */
    @POST("addons/cms/api.eye/search")
    Call<ResBase<List<ResVideoDetail.ArchivesInfoBean>>> search(@Query("keyword") String keyword);
}
