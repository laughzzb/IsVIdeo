package com.is.feature_user.ui.resetpwd;

import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.is.feature_user.R;
import com.is.feature_user.databinding.ActivityResetpwdBinding;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

@Route(path = ARoutePath.User.ACTIVITY_RESETPWD)
public class ResetpwdActivity extends BaseActivity<ActivityResetpwdBinding, ResetpwdViewModel> {

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());

        mViewModel.getCode().observe(this, s -> {
            mViewModel.updateEnableResetBtnStatus();
        });
        mViewModel.getPassword1().observe(this, s -> {
            mViewModel.updateEnableResetBtnStatus();
        });
        mViewModel.getPassword2().observe(this, s -> {
            mViewModel.updateEnableResetBtnStatus();
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected ResetpwdViewModel getViewModel() {
        return new ViewModelProvider(this).get(ResetpwdViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_resetpwd;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }


}