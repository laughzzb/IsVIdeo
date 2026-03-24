package com.is.feature_user.ui.login;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_user.R;
import com.is.feature_user.databinding.ActivityLoginBinding;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

import java.lang.ref.WeakReference;

@Route(path = ARoutePath.User.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    //  新增：保存观察者引用，便于清理
    private Observer<String> mMobileObserver;
    private Observer<String> mCodeObserver;
    private Observer<Boolean> mLoginSuccessObserver;

    // 新增：保存点击监听器引用
    private View.OnClickListener mSafeClickListener;
    private View.OnClickListener mReturnClickListener;

    @Override
    protected void initView() {
        // 沉浸式布局
        StatusBarUtils.addStatusBarHeight2View(mViewDataBinding.getRoot(),
                mViewDataBinding.ivReturn, mViewDataBinding.icSafe, mViewDataBinding.ivInstall);

        //  修改：保存观察者引用
        mMobileObserver = mobile -> mViewModel.updateEnableLoginBtnStatus();
        mCodeObserver = code -> mViewModel.updateEnableLoginBtnStatus();
        mLoginSuccessObserver = isLoginSuccess -> {
            if (isLoginSuccess != null && isLoginSuccess) {
                finish(); // 登录成功后，关闭当前页面
            }
        };

        //  修改：使用保存的观察者
        mViewModel.getUserMobile().observe(this, mMobileObserver);
        mViewModel.getCode().observe(this, mCodeObserver);
        mViewModel.getLoginSuccess().observe(this, mLoginSuccessObserver);

        initAgreementText();

        // 修改：保存点击监听器引用
        mSafeClickListener = v -> ARouter.getInstance().build(ARoutePath.User.ACTIVITY_AGREEMENT).navigation();
        mReturnClickListener = v -> finish();

        mViewDataBinding.icSafe.setOnClickListener(mSafeClickListener);
        mViewDataBinding.ivReturn.setOnClickListener(mReturnClickListener);
    }

    private void initAgreementText() {
        String string = "请阅读并同意《用户协议》和《隐私政策》";
        SpannableString spannableString = new SpannableString(string);

        // 关键修改：使用静态内部类避免内存泄漏
        SafeClickableSpan clickableSpan1 = new SafeClickableSpan(this, 1);
        SafeClickableSpan clickableSpan2 = new SafeClickableSpan(this, 2);

        spannableString.setSpan(clickableSpan1, 6, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan2, 14, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mViewDataBinding.cbAgreen.setText(spannableString);
        // 允许文本控件显示一个可以点击的链接
        mViewDataBinding.cbAgreen.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
        // 初始化数据
    }

    @Override
    protected LoginViewModel getViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    // 关键修改：静态内部类避免内存泄漏
    private static class SafeClickableSpan extends ClickableSpan {
        private final WeakReference<LoginActivity> activityRef;
        private final int type; // 1: 用户协议, 2: 隐私政策

        SafeClickableSpan(LoginActivity activity, int type) {
            this.activityRef = new WeakReference<>(activity);
            this.type = type;
        }

        @Override
        public void onClick(@NonNull View widget) {
            LoginActivity activity = activityRef.get();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }

            if (type == 1) {
                activity.showToast("假装显示了一个用户协议");
                ARouter.getInstance().build(ARoutePath.User.ACTIVITY_AGREEMENT).navigation();
            } else if (type == 2) {
                ARouter.getInstance().build(ARoutePath.User.ACTIVITY_AGREEMENT).navigation();
            }
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLACK);
            ds.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    //  新增：显示Toast的辅助方法
    private void showToast(String message) {
        if (!isFinishing() && !isDestroyed()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

}