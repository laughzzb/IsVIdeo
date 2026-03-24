package com.is.libbase.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.is.libbase.R;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.databinding.LayoutStatusViewBinding;
import com.is.network.Config.ErrorStatusConfig;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {
    protected VM mViewModel;

    protected V mViewDataBinding;
    protected LayoutStatusViewBinding mLayoutStatusViewBinding;
    private ProgressBar mProgressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //从子类获取的布局id，与dataBinding关联
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        mViewDataBinding.setLifecycleOwner(getViewLifecycleOwner());

        VM viewModel = getViewModel();
        if (viewModel != null) {
            mViewModel = viewModel;
        }


        //在父类中使用viewmodel在xml中的变量名，关联具体的viewmodel。 效果等同于mViewDataBinding.setViewModel(mViewModel);
        int bindingVariableId = getBindingVariableId();
        if (bindingVariableId != 0) {
            mViewDataBinding.setVariable(bindingVariableId, mViewModel);
            //关联完ViewModel后，实时更新数据
            mViewDataBinding.executePendingBindings();
        }

        ARouter.getInstance().inject(this);
        initView();
        initData();
        initToast();
        initStatusView();
        intProgressBar();
        if (mViewModel != null) {
            mViewModel.getStartLoginAction().observe(getViewLifecycleOwner(), aBoolean -> {
                //true表示需要打开登录页
                if (aBoolean) {
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_LOGIN).navigation();
                }
            });
        }
        return mViewDataBinding.getRoot();
    }


    /**
     * 初始化加载样式
     */
    private void intProgressBar() {
        mProgressBar = new ProgressBar(getContext());

        if (mViewDataBinding.getRoot() instanceof ConstraintLayout) {
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            mProgressBar.setLayoutParams(layoutParams);
            mProgressBar.setVisibility(View.GONE);//默认不可见
            ConstraintLayout constraintLayout = (ConstraintLayout) mViewDataBinding.getRoot();
            constraintLayout.addView(mProgressBar);
        }
    }

    private void initToast() {
        if (mViewModel != null) {
            mViewModel.getToastText().observe(getViewLifecycleOwner(), text -> {
                if (text != null && !text.isEmpty()) {
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                }
            });
            //控制加载样式是否显示
            mViewModel.getShowLoading().observe(getViewLifecycleOwner(), show -> {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
        }
    }

    private void initStatusView() {
        //动态添加布局
        ViewGroup root = (ViewGroup) mViewDataBinding.getRoot();

        // 移除已存在的状态视图
        if (mLayoutStatusViewBinding != null && mLayoutStatusViewBinding.getRoot().getParent() != null) {
            ((ViewGroup) mLayoutStatusViewBinding.getRoot().getParent()).removeView(mLayoutStatusViewBinding.getRoot());
        }
        //后面两个：是否有副布局，要不要加入进去
        mLayoutStatusViewBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.layout_status_view, null, false);


        View statusViewRoot = mLayoutStatusViewBinding.getRoot();
        statusViewRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.addView(statusViewRoot);


        if (mViewModel != null) {


            //错误状态显示
            //getViewLifecycleOwner()：通过这个方法获取当前视图声明周期的所有者，以避免造成内存泄露
            mViewModel.getErrorCode().observe(getViewLifecycleOwner(), errorCode -> {
                String content = "";
                switch (errorCode) {
                    case ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL:
                        content = "网络错误，请检查网络";
                        break;
                    case ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN:
                        content = "请登录后再操作";
                        break;
                    case ErrorStatusConfig.ERROR_STATUS_EMPTY:
                        content = "当前没有更多的数据了";
                        break;
                    case ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR:
                        content = "服务器异常";
                        break;
                    case ErrorStatusConfig.ERROR_STATUS_NORMAL:
                    default:
                        content = "";
                        break;
                }
                LayoutStatusViewBinding layoutStatusView = mLayoutStatusViewBinding;
                boolean isVisibility = errorCode != ErrorStatusConfig.ERROR_STATUS_NORMAL;
                layoutStatusView.clStatusView.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
                layoutStatusView.tvLabel.setText(content);
            });
        }

    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract VM getViewModel();

    protected abstract int getLayoutResId();

    protected abstract int getBindingVariableId();
}
