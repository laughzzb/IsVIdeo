package com.is.feature_user.ui.settings;

import android.util.Log;

import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_user.R;
import com.is.feature_user.config.UserConfig;
import com.is.feature_user.databinding.ActivitySettingsBinding;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.ui.dialog.YesOrNoDialog;
import com.is.libbase.utils.StatusBarUtils;

@Route(path = ARoutePath.User.ACTIVITY_SETTINGS)
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsViewModel> {

    private static final String TAG = "SettingsActivity";

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());

        mViewModel.getAction().observe(this, settingsAction -> {
            switch (settingsAction) {

                case NAVIGATION_TO_ACCOUNT://跳转到账户与绑定页
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_ACCOUNT).navigation();
                    break;
                case NAVIGATION_TO_PASSWORD://密码设置页
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_RESETPWD).navigation();
                    break;
                case NAVIGATION_TO_PUSH_SETTINGS://推送设置
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_PUSHSETTINGS).navigation();
                    break;
                case NAVIGATION_TO_PLAY_SETTINGS://播放设置页
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_PLAYSETTINGS).navigation();
                    break;
                case SHOW_CLEAR_CACHE_DIALOG://清除缓存
                    //显示清除缓存的弹窗
                    showClearCacheDialog();
                    break;
                case NAVIGATE_TO_USER_AGREEMENT://用户协议
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_AGREEMENT)
                            .navigation();

                    break;
                case NAVIGATE_TO_SIMPLE_PRIVACY_POLICY://隐私概要
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_SIMPLE_PRIVATE)
                            .navigation();
                    break;
                case NAVIGATE_TO_PRIVACY_POLICY://隐私政策
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_PRIVATE)
                            .navigation();
                    break;
                case NAVIGATE_TO_PERMISSION_SETTING://跳转到权限设置
                    break;
                case NAVIGATE_TO_USER_INFO_MENU://跳转到用户信息清单
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_USER_INFO)
                            .navigation();
                    break;
                case NAVIGATE_TO_ABOUT_US://跳转到关于我们
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_ABOUT_ME).navigation();
                    break;
                case FINISH:
                    finish();
                    Log.i(TAG, "initView: 关闭");
                    break;
                case SHOW_LOGOUT_DIALOG://是否需要退出
                    showLogoutDialog();
                    break;
                case NAVIGATE_TO_LOGIN://登录
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_LOGIN).navigation();
                    break;

            }
        });
    }

    /**
     * 是否退出登录的弹窗
     */
    private void showLogoutDialog() {

        YesOrNoDialog.showDialog(this, "提示", "是否退出登录",
                new YesOrNoDialog.Callback() {
                    @Override
                    public void onConfirm() {
                        //点击确定，退出
                        mViewModel.logout();
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    private void showClearCacheDialog() {
        YesOrNoDialog.showDialog(this, "清除缓存", "是否清除当前APP相关缓存",
                new YesOrNoDialog.Callback() {
                    @Override
                    public void onConfirm() {
                        //点击确定，清除缓存
                        mViewModel.clearCache();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    @Override
    protected void initData() {


    }

    @Override
    protected SettingsViewModel getViewModel() {
        return new ViewModelProvider(this).get(SettingsViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_settings;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}