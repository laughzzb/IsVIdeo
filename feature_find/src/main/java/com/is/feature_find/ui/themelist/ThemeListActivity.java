package com.is.feature_find.ui.themelist;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_find.BR;
import com.is.feature_find.R;
import com.is.feature_find.adapter.ThemeListAdapter;
import com.is.feature_find.databinding.ActivityThemeListBinding;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

import java.util.ArrayList;

@Route(path = ARoutePath.Find.ACTIVITY_THEME_LIST)
public class ThemeListActivity extends BaseActivity<ActivityThemeListBinding,ThemeListViewModel> implements ThemeListAdapter.OnItemClickListener{

    private ThemeListAdapter mAdapter;


    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2View(mViewDataBinding.getRoot(),mViewDataBinding.ivBack,mViewDataBinding.tvTitle);

        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ThemeListAdapter();
        mAdapter.setOnItemClickListener((ThemeListAdapter.OnItemClickListener) this);
        mViewDataBinding.recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        //关联数据到recyclerview适配器
        mViewModel.getDatas().observe( this,datas ->{
            mAdapter.setDatas(datas);
        });
        //请求主题歌单的数据
        mViewModel.requestData();

    }

    public void onVideoClick(int videoId){
        //点击列表中的视频 跳转到播放页
        ARouter.getInstance()
                .build(ARoutePath.Video.ACTIVITY_VIDEODETAIL)
                .withInt(ARoutePath.Video.KEY_VIDEO_ID,videoId)
                .navigation();
    }

    @Override
    protected ThemeListViewModel getViewModel() {
        return new ViewModelProvider(this).get(ThemeListViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_theme_list;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}
