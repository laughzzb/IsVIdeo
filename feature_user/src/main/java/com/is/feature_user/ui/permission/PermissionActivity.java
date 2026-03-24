package com.is.feature_user.ui.permission;

import android.os.Bundle;


import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.is.feature_user.R;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;

@Route(path = ARoutePath.User.ACTIVITY_PERMISSION)
public class PermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);


    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        return new ViewModelProvider(this).get(PermissionViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_permission;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}