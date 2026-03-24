package com.is.libbase.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.is.libbase.R;
import com.is.libbase.utils.StatusBarUtils;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected VM mViewModel;
    protected V mViewDataBinding;
    private ProgressBar mProgressBar;
    // 新增：保存观察者引用，用于在 onDestroy 中清理
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        //要放在下面两条的前面，不然会报错
        initDataBinding();

        //开启沉浸式效果
        StatusBarUtils.setImmerseStatusBar(this);


        ARouter.getInstance().inject(this);//不写接收不到ARouter的参数


        intProgressBar();
        initView();
        initData();
    }


    private void initDataBinding() {
            //从子类获取的布局id，与dataBinding关联
            mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResId());
            mViewDataBinding.setLifecycleOwner(this);

            //在父类中使用viewmodel在xml中的变量名，关联具体的viewmodel。 效果等同于mViewDataBinding.setViewModel(mViewModel);
            mViewDataBinding.setVariable(getBindingVariableId(), mViewModel);
            //关联完ViewModel后，实时更新数据
            mViewDataBinding.executePendingBindings();

    }
    /**
     * 初始化加载样式
     */
    private void intProgressBar() {


        // 新增：检查是否已经添加了 ProgressBar
        if (mProgressBar != null && mProgressBar.getParent() != null) {
            ((ViewGroup) mProgressBar.getParent()).removeView(mProgressBar);
        }
        mProgressBar = new ProgressBar(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setVisibility(View.GONE);//默认不可见

        // 修改：确保根布局是 ConstraintLayout
        if (mViewDataBinding.getRoot() instanceof ConstraintLayout) {
            ConstraintLayout constraintLayout = (ConstraintLayout) mViewDataBinding.getRoot();
            constraintLayout.addView(mProgressBar);
        }
    }


    private void initViewModel() {
        //从子类获取到的viewModel 赋值给ViewModel
        mViewModel = getViewModel();

        if (mViewModel != null) {
            //控制是否显示弹窗信息
            mViewModel.getToastText().observe(this, text -> {
                if (text != null && !text.isEmpty()) {
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                }
            });
            //控制加载样式是否显示
            mViewModel.getShowLoading().observe(this, show -> {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
            //观察到需要关闭页面
            mViewModel.getFinish().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean finish) {
                    if (finish) {
                        finish();//关闭页面
                    }
                }
            });
        }



    }


    protected abstract void initView();//如果子类需要做一些视图初始化调用

    protected abstract void initData();//如果子类需要做一些数据的初始化


    protected abstract VM getViewModel();

    protected abstract int getLayoutResId();

    protected abstract int getBindingVariableId();
}

