package com.is.mediaPlayer.ui.videolist;

import com.is.libbase.base.list.BaseListModel;
import com.is.libbase.config.ARoutePath;
import com.is.mediaPlayer.api.MediaPlayerApiServiceProvider;
import com.is.mediaPlayer.api.MediaPlayerService;
import com.is.data_video.bean.ResVideo;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;
import com.is.network.bean.ResList;

import retrofit2.Call;

public class VideoListModel extends BaseListModel {
    private static final String TAG = "VideoListModel";

    private int mPageType;


    /**
     * 请求推荐页，日报页的数据
     * @param isFirst 是否第一次加载
     */
    @Override
    public void requestDates( boolean isFirst) {
        if (isFirst) {
            mPage = 1;
        } else {
            mPage++;
        }
        MediaPlayerService apiService = MediaPlayerApiServiceProvider.getApiService();
        Call<ResBase<ResList<ResVideo>>> call;
        if (mPageType == ARoutePath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND) {
            call = apiService.getRecommend(mPage, mLimit);
        } else {
            call = apiService.getDAily(mPage, mLimit);
        }

        ApiCall.enqueueList(call, new ApiCall.ApiListCallback() {
            @Override
            public void onSuccess(ResList result) {
                mListener.OnLoadFinish(isFirst, result);
            }

            @Override
            public void onError(int errorCode, String message) {
                mListener.OnLoadFail(errorCode);
            }

        });
    }

    public void setPageType(int pageType) {
        mPageType = pageType;
    }
}