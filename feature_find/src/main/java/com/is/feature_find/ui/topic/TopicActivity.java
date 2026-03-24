package com.is.feature_find.ui.topic;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_find.BR;
import com.is.feature_find.R;
import com.is.feature_find.adapter.TopicAdapter;
import com.is.feature_find.databinding.ActivityTopicBinding;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

@Route(path = ARoutePath.Find.ACTIVITY_TOPIC)
public class TopicActivity extends BaseActivity<ActivityTopicBinding, TopicViewModel>{

    private TopicAdapter mAdapter;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2View(mViewDataBinding.getRoot()
                ,mViewDataBinding.ivFinish,mViewDataBinding.ivLogo);//获取根布局
        //设置列表啊！！！！不然数据不显示
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TopicAdapter();
        mViewDataBinding.recyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {
        mViewModel.getDatas().observe(this,datas->{
            mAdapter.setDatas(datas);
        });
        mViewModel.requestTopicDatas();//请求数据
    }

    @Override
    protected TopicViewModel getViewModel() {
        return new ViewModelProvider(this).get(TopicViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topic;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}
