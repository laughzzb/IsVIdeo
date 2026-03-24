package com.is.mediaPlayer.ui.search;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;
import com.is.mediaPlayer.BR;
import com.is.mediaPlayer.R;
import com.is.mediaPlayer.adapter.SearchAdapter;
import com.is.mediaPlayer.databinding.ActivitySearchBinding;

@Route(path = ARoutePath.Video.ACTIVITY_SEARCH)
public class SearchActivity extends BaseActivity<ActivitySearchBinding,SearchViewModel> implements SearchAdapter.ItemCLickListener{

    private SearchAdapter mAdapter;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
       //初始化列表
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter();
        mAdapter.setItemCLickListener(this);
        mViewDataBinding.recyclerView.setAdapter(mAdapter);

        mViewDataBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    //搜索
                    mViewModel.searchByKeyword();
                    return true;
                }else {
                    return false;
                }
            }
        });
        mViewModel.getSearchKeyword().observe(this,keyword->{
            //对搜索关键词做监听，只要长度大于0，就改变清空按钮的显示状态
            mViewModel.update(keyword !=null && keyword.length()>0 );
        });

    }

    @Override
    protected void initData() {
        mViewModel.getDatas().observe(this,datas->{
            mAdapter.setDatas(datas);
        });

    }

    @Override
    protected SearchViewModel getViewModel() {
        return new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    public void onItemClick(int videoId) {
        //跳转到视频详情页
        ARouter.getInstance()
                .build(ARoutePath.Video.ACTIVITY_VIDEODETAIL)
                .withInt(ARoutePath.Video.KEY_VIDEO_ID, videoId)
                .navigation();
    }
}
