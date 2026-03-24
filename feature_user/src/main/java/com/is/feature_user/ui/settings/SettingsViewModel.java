package com.is.feature_user.ui.settings;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.evevntbus.MessageEvent;
import com.is.network.bean.ResBase;

public class SettingsViewModel extends BaseViewModel{
    private static final String TAG = "SettingsViewModel";

    private final SettingsModel mModel;

    private MutableLiveData<String> mMobile = new MutableLiveData<>();//手机号
    private MutableLiveData<String> mCacheSize = new MutableLiveData<>();//缓存大小
    private MutableLiveData<Integer> mExitLoginBtnVisibility = new MutableLiveData<>();//是否显示退出登录的按钮

    private MutableLiveData<SettingsAction> mAction = new MutableLiveData<>();

    public MutableLiveData<Integer> getExitLoginBtnVisibility() {
        return mExitLoginBtnVisibility;
    }


    public MutableLiveData<String> getCacheSize() {
        return mCacheSize;
    }

    public MutableLiveData<String> getMobile() {
        return mMobile;
    }
    public MutableLiveData<SettingsAction> getAction() {
        return mAction;
    }




    /**
     * 刷新缓存大小
     */
    public void refreshCashSize(){
        String cacheSize = mModel.getCacheSize();
        mCacheSize.setValue(cacheSize);//将值设置到变量中
    }

    public SettingsViewModel() {
        mModel = new SettingsModel();

        refreshLoginStatus();
        refreshCashSize();//显示缓存
    }

    /**
     * 第一次进入页面，以及退出登录，需要调用这个方法，刷新当前的页面显示
     */
    private void refreshLoginStatus() {
        boolean login = mModel.isLogin();
        mExitLoginBtnVisibility.setValue(login? View.VISIBLE : View.GONE);//登录了就显示退出登录按钮
        mMobile.setValue(mModel.getMobile());
        Log.i("SettingsVM", "refreshLoginStatus: isLogin=" + login + ", visibility=" + (login ? View.VISIBLE : View.GONE));
    }

    /**
     * 清除缓存
     */
    public void clearCache(){
        showLoading(true);
        boolean b = mModel.clearCache();
        if (b){
            refreshCashSize();//刷新缓存显示
            showLoading(false);
            showToast("缓存清除成功！");
        }else {
            showToast("缓存清除失败，请手动前往设置页处理");
            showLoading(false);
        }

    }

    /**
     * 退出登录
     * 1、清除已登录的用户信息，
     * 2.告诉服务端退出登录
     */
    public void logout() {
        showLoading(true);
        mModel.logout(new IRequestCallback<ResBase<ResBase>>() {
            @Override
            public void onLoadFinish(ResBase<ResBase> dates) {
                //发送一个退出登录的状态给user页面,刷新用户信息
                MessageEvent.LoginStatusEvent.post(false);
                refreshLoginStatus();
                showToast(dates.getMsg());
                showLoading(false);
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showToast(message);
                showLoading(false);
            }
        });

    }


    /**
     * 枚举
     */
    public enum SettingsAction {

        FINISH,//关闭页面
        SHOW_LOGOUT_DIALOG,//显示退出登录的弹窗
        NAVIGATION_TO_ACCOUNT,//跳转到账户与绑定
        NAVIGATION_TO_PASSWORD,//跳转到设置密码页
        NAVIGATION_TO_PUSH_SETTINGS,//跳转到推送设置
        NAVIGATION_TO_PLAY_SETTINGS,//跳转到播放设置
        SHOW_CLEAR_CACHE_DIALOG,//显示清除缓存对话框
        NAVIGATE_TO_USER_AGREEMENT,//跳转到用户协议
        NAVIGATE_TO_SIMPLE_PRIVACY_POLICY,//跳转到概要隐私政策
        NAVIGATE_TO_PRIVACY_POLICY,//跳转到隐私政策
        NAVIGATE_TO_PERMISSION_SETTING,//跳转到权限设置
        NAVIGATE_TO_USER_INFO_MENU,//跳转到用户信息清单
        NAVIGATE_TO_ABOUT_US,//跳转到关于我们
        NAVIGATE_TO_LOGIN//跳转到关于我们

    }

    /**
     * 设置密码点击事件
     */
    public void onPasswordSettingClick() {

        if (mModel.isLogin()){
            mAction.setValue(SettingsAction.NAVIGATION_TO_PASSWORD);
        }else {
            mAction.setValue(SettingsAction.NAVIGATE_TO_LOGIN);//转到登录界面

        }

    }


    /**
     * 清除缓存点击事件
     */
    public void onClearCacheClick() {
        mAction.setValue(SettingsAction.SHOW_CLEAR_CACHE_DIALOG);

    }

    /**
     * 播放设置点击事件
     */
    public void onPlaySettingsCLick() {
        mAction.setValue(SettingsAction.NAVIGATION_TO_PUSH_SETTINGS);
    }

    /**
     * 推送设置点击事件
     */
    public void onPushCLick() {
        mAction.setValue(SettingsAction.NAVIGATION_TO_PLAY_SETTINGS);
    }

    /**
     * 用户协议点击事件
     */
    public void onUserAgreementClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_USER_AGREEMENT);
    }

    /**
     * 隐私政策概要点击事件
     */
    public void onSimplePrivacyPolicyClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_SIMPLE_PRIVACY_POLICY);
    }

    /**
     * 隐私政策点击事件
     */
    public void onPrivacyPolicyClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_PRIVACY_POLICY);
    }

    /**
     * 隐私权限设置点击事件
     */
    public void onPermissionSettingsClick() {
    }

    /**
     * 个人信息收集清单点击事件
     */
    public void onUserInfoMenusClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_USER_INFO_MENU);

    }

    /**
     * 关于我们点击事件
     */
    public void onAboutUsClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_ABOUT_US);

    }

    /**
     * 退出登录点击事件
     */
    public void onLogoutClick() {
         mAction.setValue(SettingsAction.SHOW_LOGOUT_DIALOG);//是否需要退出登录

    }




    /**
     * 账户与绑定点击事件
     */
    public void onAccountBindClick() {
        if (mModel.isLogin()){
            mAction.setValue(SettingsAction.NAVIGATION_TO_ACCOUNT);//是否绑定账号
        }else {
            mAction.setValue(SettingsAction.NAVIGATE_TO_LOGIN);//转到登录界面

        }
    }

}
