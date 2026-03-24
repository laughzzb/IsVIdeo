package com.is.mediaPlayer.ui.videolist;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.data_video.bean.ResVideo;
import com.is.data_video.bean.ResVideo;
import com.is.libbase.base.list.BaseListFragment;
import com.is.libbase.config.ARoutePath;
import com.is.mediaPlayer.BR;
import com.is.mediaPlayer.adapter.VideoAdapter;


import java.util.List;

@Route(path = ARoutePath.Video.FRAGMENT_VIDEO_LIST)
public class VideoListFragment extends BaseListFragment<ResVideo> implements VideoAdapter.ItemClickListenner {

    @Autowired(name = ARoutePath.Video.KEY_VIDEO_LIST_TYPE)
    public int mPageType;
    @Autowired(name = ARoutePath.Video.KEY_VIDEO_LIST_STYLE)
    public boolean mStyle;//是否需要把item中的文本改为白色
    private VideoAdapter mAdapter;

    public VideoListFragment() {
    }

    @Override
    protected void initData() {
        VideoListViewModel model = (VideoListViewModel) mViewModel;
        model.setPageType(mPageType);
        super.initData();

    }

    @Override
    protected void initView() {
        super.initView();//记得调用super
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new VideoAdapter(this);
        if (mStyle){
            mAdapter.setItemWhite(true);
        }
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected void onDatesRequestSuccess(List<ResVideo> list) {
        mAdapter.setVideos(list);

    }

    @Override
    protected VideoListViewModel getViewModel() {
        // 正确初始化！用 ViewModelProvider
        return new ViewModelProvider(this).get(VideoListViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }


    public void onVideoItemClick(int id) {
        //视频详情页
        ARouter.getInstance()
                .build(ARoutePath.Video.ACTIVITY_VIDEODETAIL)
                .withInt(ARoutePath.Video.KEY_VIDEO_ID, id)
                .navigation();
    }

    /**
     * 测试调用的方法，用来测试错误码，并不是实际存在的功能
     * @param value
     */
//    public void setErrorCode(Integer value) {
//        mViewModel.getErrorCode().setValue(value);
//    }
}
