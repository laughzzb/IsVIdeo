package com.is.mediaPlayer.ui.videolist;

import com.is.libbase.base.list.BaseListViewModel;
import com.is.data_video.bean.ResVideo;

public class VideoListViewModel extends BaseListViewModel <ResVideo,VideoListModel>   {


    private int mPageType;

    public VideoListViewModel() {
        super(new VideoListModel());
    }


    public void setPageType(int pageType) {
        mPageType = pageType;
        mModel.setPageType(mPageType);
    }
}
