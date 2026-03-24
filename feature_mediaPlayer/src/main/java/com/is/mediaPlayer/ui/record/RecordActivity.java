package com.is.mediaPlayer.ui.record;



import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;
import com.is.mediaPlayer.R;
import com.is.mediaPlayer.adapter.SearchAdapter;
import com.is.mediaPlayer.adapter.VideoHistoryAdapter;
import com.is.mediaPlayer.databinding.ActivityRecordBinding;
import com.is.mediaPlayer.databinding.ActivitySearchBinding;
import com.is.mediaPlayer.db.VideoHistory;

import java.util.List;

@Route(path = ARoutePath.Video.ACTIVITY_RECORD)
public class RecordActivity extends BaseActivity<ActivityRecordBinding,RecordViewModel>implements VideoHistoryAdapter.ItemClickListenner {


    private VideoHistoryAdapter mAdapter;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2View(mViewDataBinding.getRoot(),mViewDataBinding.ivBack, mViewDataBinding.tvTitle,mViewDataBinding.tvEdit);
        //初始化列表
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VideoHistoryAdapter();
        mAdapter.setItemClickListenner(this);
        mViewDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mViewModel.getDatas().observe(this, new Observer<List<VideoHistory>>() {
            @Override
            public void onChanged(List<VideoHistory> videoHistories) {
                mAdapter.setDatas(videoHistories);
            }
        });
        //选择状态发送变化，通知适配器更新选择状态。改变样式
        mViewModel.getSelectStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean selectStatus) {
                mAdapter.updateSelectStatus(selectStatus);
            }
        });
        mViewModel.requestHistory();

    }

    @Override
    public void onItemClick(int videoId) {
        //跳转到视频详情页
        ARouter.getInstance()
                .build(ARoutePath.Video.ACTIVITY_VIDEODETAIL)
                .withInt(ARoutePath.Video.KEY_VIDEO_ID, videoId)
                .navigation();

    }
    @Override
    public void onDelSelect(VideoHistory history, boolean isSelect) {
        mViewModel.updateDelSelectDatas(history,isSelect);
    }
    @Override
    protected RecordViewModel getViewModel() {
        return new ViewModelProvider(this).get(RecordViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_record;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}