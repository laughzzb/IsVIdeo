package com.is.feature_user.ui.collect;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.is.feature_user.R;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

@Route(path = ARoutePath.User.ACTIVITY_COLLECT)
public class CollectActivity extends BaseActivity {


    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        return new ViewModelProvider(this).get(CollectViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collect;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel ;
    }

}