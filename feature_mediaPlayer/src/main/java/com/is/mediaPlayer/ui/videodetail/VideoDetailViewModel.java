package com.is.mediaPlayer.ui.videodetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.is.libbase.base.BaseApplication;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.base.list.ListListener;
import com.is.data_video.bean.ResComment;
import com.is.data_video.bean.ResLike;
import com.is.data_video.bean.ResSendComment;
import com.is.data_video.bean.ResVideoDetail;
import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResBase;
import com.is.network.bean.ResList;

import java.util.List;

public class VideoDetailViewModel extends BaseViewModel {
    private static final String TAG = "VideoDetailViewModel";

    private final VideoDetailModel mModel;

    private MutableLiveData<String> mChannel = new MutableLiveData<>();
    //点赞和收藏是否点击，默认未点击
    private MutableLiveData<Boolean> mIsLikes = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mIsCollection = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mIsLoadMore = new MutableLiveData<>(true);//默认允许加载更多
    //视频详情数据：单一数据源
    private MutableLiveData<ResVideoDetail.ArchivesInfoBean> mVideoInfo = new MutableLiveData<>();
    //评论数据
    private MutableLiveData<List<ResComment>> mComments = new MutableLiveData<>();
    private MutableLiveData<Action> mAction = new MutableLiveData<>();


    public MutableLiveData<ResVideoDetail.ArchivesInfoBean> getVideoInfo() {
        return mVideoInfo;
    }

    public MutableLiveData<List<ResComment>> getComments() {
        return mComments;
    }

    public MutableLiveData<Boolean> getIsLoadMore() {
        return mIsLoadMore;
    }

    public VideoDetailViewModel() {
        mModel = new VideoDetailModel();
    }

    public MutableLiveData<Action> getAction() {
        return mAction;
    }

    public void setAction(MutableLiveData<Action> mAction) {
        this.mAction = mAction;
    }

    /**
     * 请求视频详情
     *
     * @param videoId
     */
    public void requestDetail(int videoId) {
        showLoading(true);
        mModel.requestDetail(videoId, new IRequestCallback<ResVideoDetail>() {
            @Override
            public void onLoadFinish(ResVideoDetail dates) {
                showLoading(false);
                ResVideoDetail.ArchivesInfoBean archivesInfo = dates.getArchivesInfo();
                List<ResComment> commentList = dates.getCommentList();

                //更新数据
                mVideoInfo.setValue(archivesInfo);
                mComments.setValue(commentList);
                mIsLikes.setValue(archivesInfo.getIslike() == 1);//1表示true
                mIsCollection.setValue(archivesInfo.getCollection() == 1);//1表示true
                mChannel.setValue("#" + archivesInfo.getChannel().getName());

                //插入浏览数据到数据库
                mModel.insertHistory(dates.getArchivesInfo());
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    /**
     * 请求评论列表
     *
     * @param isFirst 是否是第一次加载（刷新） 传true
     */
    public void requestComments(boolean isFirst) {
        int id = mVideoInfo.getValue().getId();//获取视频id
        mModel.requestComments(id, isFirst, new ListListener<ResComment>() {
            @Override
            public void OnLoadFinish(boolean isFirst, ResList<ResComment> video) {
                //更新本地数据
                List<ResComment> list = video.getList();
                //小于10条表示列表数据加载完毕
                mIsLoadMore.setValue(list != null && list.size() >= 10);

                if (isFirst) {
                    mComments.setValue(list);
                } else {
                    //不是第一次加载，就将列表里的数据都加载
                    List<ResComment> comments = mComments.getValue();
                    comments.addAll(list);
                    mComments.setValue(comments);
                }

            }

            @Override
            public void OnLoadFail(int statusCode) {
//                getErrorCode().setValue(statusCode);//业务场景不同
                if(statusCode == ErrorStatusConfig.ERROR_STATUS_EMPTY){
                    showToast("没有更多数据了");
                    mIsLoadMore.setValue(false);
                }
                Log.i(TAG, "OnLoadFail: 加载失败" + statusCode);
            }
        });
    }

    /**
     * 点赞
     */
    public void onLikeClick() {

        if (!mModel.isLogin()) {
            //未登录 不允许点赞
            showToast("未登录");
            startLogin();//打开登录页
            return;
        }

        //获取点赞状态
        Boolean islike = mIsLikes.getValue();
        ResVideoDetail.ArchivesInfoBean info = mVideoInfo.getValue();
        int aid = info.getId();

        showLoading(true);
        if (islike) {
            mModel.requestCancelLike(aid, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike dates) {
                    showLoading(false);
                    showToast(dates.getMsg());

                    //更新取消点赞后的本地数据
                    info.setIslike(0);
//                    info.setLikes(info.getLikes()-1);
                    mVideoInfo.setValue(info);
                    mIsLikes.setValue(!islike);
                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        } else {
            mModel.requestLike(aid, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike dates) {
                    showLoading(false);
                    showToast(dates.getMsg());

                    //更新点赞后的本地数据
                    info.setIslike(1);
//                    info.setLikes(info.getLikes()+1);
                    mVideoInfo.setValue(info);
                    mIsLikes.setValue(!islike);
                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        }
    }

    /**
     * 收藏
     */
    public void onCollectClick() {
        if (!mModel.isLogin()) {
            //未登录 不允许点赞
            showToast("未登录");
            startLogin();//打开登录页
            return;
        }
        //获取收藏状态
        Boolean isCollect = mIsCollection.getValue();
        ResVideoDetail.ArchivesInfoBean info = mVideoInfo.getValue();
        int id = info.getId();
        showLoading(true);
        if (isCollect) {

            mModel.requestCannelCollection(id, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike dates) {
                    showLoading(false);
                    showToast(dates.getMsg());

                    //更新收藏后的本地数据
                    info.setIscollection(1);
                    mVideoInfo.setValue(info);
                    mIsCollection.setValue(!isCollect);


                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    showLoading(false);
                    showToast(message);

                }
            });

        } else {
            mModel.requestCollection(id, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike dates) {
                    showLoading(false);
                    showToast(dates.getMsg());

                    //更新取消收藏后的本地数据
                    info.setIscollection(0);
                    mVideoInfo.setValue(info);
                    mIsCollection.setValue(!isCollect);
                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });

        }
    }

    /**
     * 评论的内容
     *
     * @param message
     */
    public void sendComment(String message) {
        if (!mModel.isLogin()) {
            //未登录 不允许点赞
            showToast("未登录");
            startLogin();//打开登录页
            return;
        }
        ResVideoDetail.ArchivesInfoBean info = mVideoInfo.getValue();
        int id = info.getId();
        showLoading(true);
        mModel.sendComment(id, message, new IRequestCallback<ResSendComment>() {
            @Override
            public void onLoadFinish(ResSendComment dates) {
                showLoading(false);
                showToast("评论成功");

                //将评论插入到评论列表中
                List<ResComment> comments = mComments.getValue();
                comments.add(0, dates.getComment());
                mComments.setValue(comments);
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }


    public MutableLiveData<String> getChannel() {
        return mChannel;
    }

    public MutableLiveData<Boolean> getIsCollection() {
        return mIsCollection;
    }

    public MutableLiveData<Boolean> getIsLikes() {
        return mIsLikes;
    }


    //删除评论
    public void deleteComment(ResComment comment) {
        showToast("删除评论");
        if (!mModel.isLogin()) {
            //未登录 不允许点赞
            showToast("未登录");
            startLogin();//打开登录页
            return;
        }
        int userId = comment.getUser_id();//评论的作者id
        //如果不是自己的评论无法删除
        if (!mModel.isAuthor(userId)) {
            showToast("不能删除别人的评论！");
            return;
        }
        //获取评论id
        int id = comment.getId();
        showLoading(true);
        mModel.deleteComment(id, new IRequestCallback<ResBase>() {
            @Override
            public void onLoadFinish(ResBase dates) {
                showLoading(false);
                showToast(dates.getMsg());

                //将评论插入到评论列表中
                List<ResComment> comments = mComments.getValue();
                if (comment != null) {
                    mComments.setValue(comments);
                    comments.remove(comment);

                }

            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    //使用友盟模块的分享调用案例
    public void share2Wechat(){
        mAction.setValue(Action.Share);
    }
    public enum Action{
        Share
    }




//    //分享
//    public void share2Wechat(){
//        ResVideoDetail.ArchivesInfoBean infoBean = mVideoInfo.getValue();
//        String title = infoBean.getTitle();
//        String description = infoBean.getDescription();
//        String url = infoBean.getFullurl();
//        Bitmap bitmap = BitmapFactory.decodeResource(BaseApplication.getContext().getResources(), com.is.library_wechat.R.mipmap.icon_logo108);
//
//        WxShareManager.getInstance().shareUrl2Wx(bitmap,title,description,url,true);
//
//
//    }
}
