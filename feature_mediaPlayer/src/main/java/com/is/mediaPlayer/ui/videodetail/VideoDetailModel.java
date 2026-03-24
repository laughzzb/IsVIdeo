package com.is.mediaPlayer.ui.videodetail;

import android.util.Log;

import com.is.libbase.base.BaseApplication;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.base.list.ListListener;
import com.is.libbase.manager.UserManager;
import com.is.mediaPlayer.api.MediaPlayerApiServiceProvider;
import com.is.mediaPlayer.api.MediaPlayerService;
import com.is.data_video.bean.ReqComment;
import com.is.data_video.bean.ReqDelectComment;
import com.is.data_video.bean.ReqVideoOperation;
import com.is.data_video.bean.ResComment;
import com.is.data_video.bean.ResLike;
import com.is.data_video.bean.ResSendComment;
import com.is.data_video.bean.ResVideoDetail;
import com.is.mediaPlayer.db.VideoHistory;
import com.is.mediaPlayer.db.VideoHistoryRepository;
import com.is.network.ApiCall;
import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResBase;
import com.is.network.bean.ResList;

import retrofit2.Call;

public class VideoDetailModel {
    private static final String TAG = "VideoDetailModel";

    public void requestDetail(int videoId, IRequestCallback<ResVideoDetail> callback) {
        MediaPlayerService apiService = MediaPlayerApiServiceProvider.getApiService();

        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResVideoDetail>> call = apiService.getVideoDetail(token, videoId);

        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResVideoDetail>>() {
            @Override
            public void onSuccess(ResBase<ResVideoDetail> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });

    }

    /**
     * 点赞
     * 需要点赞的id
     */
    public void requestLike(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call = MediaPlayerApiServiceProvider.getApiService().requestLike(token, new ReqVideoOperation(id, "like"));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {
                if (result != null && result.getCode() == 1001) {
                    Log.i(TAG, "onResponse: 数据处理成功");
                    callback.onLoadFinish(result);

                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });

    }

    public boolean isLogin() {
        boolean login = UserManager.getInstance().isLogin();
        return login;
    }

    /**
     * 取消点赞
     * 1.判断是否登录。2.获取token
     */
    public void requestCancelLike(int aid, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }

        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call = MediaPlayerApiServiceProvider.getApiService().requestCancelLike(token, new ReqVideoOperation(aid));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {
                if (result != null && result.getCode() == 1) {
                    Log.i(TAG, "onResponse: 数据处理成功");
                    callback.onLoadFinish(result);

                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }

    /**
     * 文章收藏
     */
    public void requestCollection(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call = MediaPlayerApiServiceProvider.getApiService().requestCollection(token, new ReqVideoOperation("archives", id));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {
                if (result != null && result.getCode() == 1) {
                    callback.onLoadFinish(result);
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }

    /**
     * 取消收藏
     *
     * @param id
     * @param callback
     */
    public void requestCannelCollection(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResLike> call = MediaPlayerApiServiceProvider.getApiService().requestCancelCollection(token, new ReqVideoOperation(id));
        ApiCall.enqueueCommon(call, new ApiCall.ApiCallback<ResLike>() {
            @Override
            public void onSuccess(ResLike result) {
                if (result != null && result.getCode() == 1) {
                    callback.onLoadFinish(result);
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR, result.getMsg());
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }

    /**
     * 发起评论
     *
     * @param id
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
        Call<ResBase<ResSendComment>> call = MediaPlayerApiServiceProvider.getApiService().sendComment(token, operation);
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResSendComment>>() {
            @Override
            public void onSuccess(ResBase<ResSendComment> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }

    /**
     * 请求评论列表
     */
    public void requestComments(int id,boolean isFirst, ListListener<ResComment> listener) {
        if (isFirst){
            mCommentPage = 1;
        }else {
            mCommentPage++;
        }
        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();
        Call<ResBase<ResList<ResComment>>> call = MediaPlayerApiServiceProvider.getApiService().requestCommentList(token, id, mCommentPage);
        ApiCall.enqueueList(call, new ApiCall.ApiListCallback<ResComment>() {
            @Override
            public void onSuccess(ResList<ResComment> result) {
                listener.OnLoadFinish(isFirst, result);
            }

            @Override
            public void onError(int errorCode, String message) {
                listener.OnLoadFail(errorCode);
            }
        });
    }


    /**
     * 判断当前作者id与已登录的用户，是不是同一个人
     *
     * @param authorId
     */
    public boolean isAuthor(int authorId) {
        boolean isAuthor = false;
        if (!isLogin()) {
            return false;
        }
        //当前已登录的id
        String id = UserManager.getInstance().getUSerInfo().getUser().getId();
        isAuthor = authorId == Integer.valueOf(id);//是否为同已id

        return isAuthor;
    }

    /**
     * 评论列表数据加载时用的page
     */
    private int mCommentPage = 1;
    /**
     * 删除评论
     *
     * @param id
     * @param callback
     */
    public void deleteComment(int id, IRequestCallback<ResBase> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        UserManager userManager = UserManager.getInstance();
        String token = userManager.getToken();

        Call<ResBase<ReqComment>> call = MediaPlayerApiServiceProvider.getApiService().DeleteComment(token, new ReqDelectComment(id));
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ReqComment>>() {
            @Override
            public void onSuccess(ResBase<ReqComment> result) {
                callback.onLoadFinish(result);
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }

    public void insertHistory(ResVideoDetail.ArchivesInfoBean archivesInfo) {

        VideoHistoryRepository repository = new VideoHistoryRepository(BaseApplication.getContext());
       //如果未登录userid为空
        String userId = "0";
        if (UserManager.getInstance().isLogin()){
            userId = UserManager.getInstance().getUSerInfo().getUser().getId();
        }
        //生成浏览记录
        String tag = archivesInfo.getChannel().getName();
        VideoHistory history = repository.generateVideoHIstory(userId, archivesInfo.getId()
                , archivesInfo.getTitle(), tag, archivesInfo.getDuration(),archivesInfo.getImage());
        //插入数据
        repository.insert(history);
    }
}
