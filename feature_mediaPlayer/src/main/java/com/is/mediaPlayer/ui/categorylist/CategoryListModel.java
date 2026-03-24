package com.is.mediaPlayer.ui.categorylist;

import android.util.Log;

import com.is.data_video.bean.ReqComment;
import com.is.data_video.bean.ReqVideoOperation;
import com.is.data_video.bean.ResCategoryVideoDetail;
import com.is.data_video.bean.ResLike;
import com.is.data_video.bean.ResSendComment;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.base.list.BaseListModel;
import com.is.libbase.manager.UserManager;
import com.is.mediaPlayer.api.MediaPlayerApiServiceProvider;
import com.is.mediaPlayer.api.MediaPlayerService;
import com.is.network.ApiCall;
import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResBase;
import com.is.network.bean.ResList;

import retrofit2.Call;

public class CategoryListModel extends BaseListModel {
    private static final String TAG = "CategoryListModel";

    public int mType;//区分是热门发布，还是最新发布
    public int mChannelId;//分类id

    public void requestDates(boolean isFirst) {
        if (isFirst) {
            mPage = 1;
        } else {
            mPage++;
        }
        String token = UserManager.getInstance().getToken();

        MediaPlayerService apiService = MediaPlayerApiServiceProvider.getApiService();
        Call<ResBase<ResList<ResCategoryVideoDetail>>> call = apiService.getCategoryLists(token, mType, mChannelId, mPage, mLimit);

       ApiCall.enqueueList(call, new ApiCall.ApiListCallback() {
           @Override
           public void onSuccess(ResList result) {
               mListener.OnLoadFinish(isFirst,result);
           }

           @Override
           public void onError(int errorCode, String message) {
               mListener.OnLoadFail(errorCode);
           }
       });
    }

    public void setArgments(int type, int id) {
        mType = type;
        mChannelId = id;
    }

    public boolean isLogin() {
        return UserManager.getInstance().isLogin();//是否登录
    }

    /**
     * 发送评论
     *
     * @param id
     * @param content
     * @param callback
     */
    public void sendComment(int id, String content, IRequestCallback<ResSendComment> callback) {

        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        ReqComment operation = new ReqComment(id, content);
        Call<ResBase<ResSendComment>> call =  MediaPlayerApiServiceProvider.getApiService().sendComment(token, operation);
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResSendComment>>() {
            @Override
            public void onSuccess(ResBase<ResSendComment> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String meesage) {
                callback.onLoadFail(errorCode, meesage);
            }
        });

    }

    /**
     * 点赞
     *
     * @param id 需要点赞的id
     */
    public void requestLike(int id, IRequestCallback<ResLike> callback) {

        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }

        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call =  MediaPlayerApiServiceProvider.getApiService().requestLike(token, new ReqVideoOperation(id, "like"));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {

                if (result != null && result.getCode() == 1001) {
                    Log.i(TAG, "onResponse: 数据请求成功");
                    callback.onLoadFinish(result);
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String meesage) {
                callback.onLoadFail(errorCode, meesage);
            }
        });
    }


    /**
     * 取消点赞
     *
     * @param id 需要取消点赞的id
     */
    public void requestCancelLike(int id, IRequestCallback<ResLike> callback) {

        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }

        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call =  MediaPlayerApiServiceProvider.getApiService().requestCancelLike(token, new ReqVideoOperation(id));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {

                if (result != null && result.getCode() == 1) {
                    Log.i(TAG, "onResponse: 数据请求成功");
                    callback.onLoadFinish(result);
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String meesage) {
                callback.onLoadFail(errorCode, meesage);
            }
        });
    }

    /**
     * 收藏
     *
     * @param id 需要点赞的id
     */
    public void requestCollection(int id, IRequestCallback<ResLike> callback) {

        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }

        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call =  MediaPlayerApiServiceProvider.getApiService().requestCollection(token, new ReqVideoOperation("archives", id));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {

                if (result != null && result.getCode() == 1) {
                    Log.i(TAG, "onResponse: 数据请求成功");
                    callback.onLoadFinish(result);
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String meesage) {
                callback.onLoadFail(errorCode, meesage);
            }
        });
    }

    /**
     * 取消收藏
     *
     * @param id 需要取消点赞的id
     */
    public void requestCancelCollection(int id, IRequestCallback<ResLike> callback) {

        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }

        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call =  MediaPlayerApiServiceProvider.getApiService().requestCancelCollection(token, new ReqVideoOperation(id));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {

                if (result != null && result.getCode() == 1) {
                    Log.i(TAG, "onResponse: 数据请求成功");
                    callback.onLoadFinish(result);
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String meesage) {
                callback.onLoadFail(errorCode, meesage);
            }
        });
    }

}
