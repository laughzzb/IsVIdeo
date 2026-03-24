package com.is.feature_user.ui.user;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.evevntbus.MessageEvent;
import com.is.libbase.manager.ResUser;
import com.is.libbase.manager.UserInfo;
import com.is.network.bean.ResBase;

public class UserViewModel extends BaseViewModel {
    private static final String TAG = "UserViewModel";

    private final UserModel mModel;
    private MutableLiveData<String> mAvater = new MutableLiveData<>();
    private MutableLiveData<String> mNickName = new MutableLiveData<>();

    private MutableLiveData<String> mFans = new MutableLiveData<>();
    private MutableLiveData<String> mMedel = new MutableLiveData<>();
    private MutableLiveData<String> mFollow = new MutableLiveData<>();
    private MutableLiveData<String> mBio = new MutableLiveData<>();
    private MutableLiveData<UserAction> mAction = new MutableLiveData<>();
    private MutableLiveData<Integer> mShowLogoutBtn = new MutableLiveData<>();//判断是否显示退出登录按钮


    public MutableLiveData<Integer> getShowLogoutBtn() {
        return mShowLogoutBtn;
    }

    public MutableLiveData<UserAction> getAction() {
        return mAction;
    }

    public MutableLiveData<String> getBio() {
        return mBio;
    }


    public MutableLiveData<String> getFollow() {
        return mFollow;
    }

    public MutableLiveData<String> getMedel() {
        return mMedel;
    }

    public MutableLiveData<String> getFans() {
        return mFans;
    }

    public MutableLiveData<String> getNickName() {
        return mNickName;
    }

    public MutableLiveData<String> getAvater() {
        return mAvater;
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

    public enum UserAction {
        NAVIGATE_TO_LOGIN,//跳转到登录页
        NAVIGATE_TO_EDIT_INFO,//跳转到用户信息编辑
        NAVIGATE_TO_RECORD,//跳转到播放记录
        NAVIGATE_TO_COLLECT,//跳转到收藏记录
        SHOW_LOGOUT_DIALOG,//显示退出登录窗口
        NAVIGATE_TO_SETTINGS,//跳转到设置页面

    }


    public UserViewModel() {
        mModel = new UserModel();
        //进入到user页面后，根据登录状态更新ui
        boolean login = mModel.isLogin();
        mShowLogoutBtn.setValue(login? View.VISIBLE : View.GONE);//登录了就显示退出登录按钮
        loadUSerInfo(login);
    }

    public void loadUSerInfo(boolean login) {
        if (login) {
            showLoading(true);
            mModel.loadUserInfo(new ILoadUSerInfoCallback() {
                @Override
                public void onLoadSuccess(ResUser user) {
                    Log.i("UserVM", "onLoadSuccess: 成功！nickname=" + (user.getUser() != null ? user.getUser().getNickname() : "null"));
                    showLoading(false);
                    updateUSerInfo(user);
                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    Log.i("UserVM", "onLoadFail: 失败！errorCode=" + errorCode + ", message=" + message);
                    showLoading(false);
                    notLoginUpdateUSerInfo();
                }
            });
            mShowLogoutBtn.setValue(View.VISIBLE);
        } else {
            notLoginUpdateUSerInfo();

        }
    }

    private void notLoginUpdateUSerInfo() {

        //如果没登录，或获取不到登录的信息，那么传个空的用来更新ui
        ResUser user = new ResUser();
        user.setUser(new UserInfo());
        updateUSerInfo(user);

        //没登录的时候，刷新退出登录按钮的状态
        mShowLogoutBtn.setValue(View.INVISIBLE);
    }

    /**
     * 编辑资料
     */
    public void onEditUserINfoClick() {
        boolean login = mModel.isLogin();
        //如果没有登录就去登录页
        mAction.setValue(login ? UserAction.NAVIGATE_TO_EDIT_INFO : UserAction.NAVIGATE_TO_LOGIN);

    }

    /**
     * 观影记录点击事件
     */
    public void onRecordClick() {
        mAction.setValue(UserAction.NAVIGATE_TO_RECORD );
    }

    /**
     * 收藏点击事件
     */
    public void onCollectClick() {
        boolean login = mModel.isLogin();
        //如果没有登录，跳转到登录界面
        mAction.setValue(login ? UserAction.NAVIGATE_TO_COLLECT : UserAction.NAVIGATE_TO_LOGIN);
    }

    /**
     * 退出登录点击事件
     */
    public void onLogoutClick() {
        boolean login = mModel.isLogin();
        //如果没有登录，跳转到登录界面
        mAction.setValue(login ? UserAction.SHOW_LOGOUT_DIALOG: UserAction.NAVIGATE_TO_LOGIN);
    }

    /**
     * 设置按钮点击事件
     */
    public void SettingsPage(){
        boolean login = mModel.isLogin();
        mAction.setValue(UserAction.NAVIGATE_TO_SETTINGS);
    }


    /**
     * 更新用户数据的显示
     *
     * @param user
     */
    private void updateUSerInfo(ResUser user) {
        Log.i("UserVM", "updateUSerInfo: 开始更新，avatar=" + (user.getUser() != null ? user.getUser().getAvatar() : "null"));
        // ... 你的 setValue 代码不变
        Log.i("UserVM", "updateUSerInfo: setValue 完成");
        String avatar = user.getUser().getAvatar();

        if (avatar != null && !avatar.isEmpty()) {
            mAvater.setValue(avatar);
        } else {
            mAvater.setValue(null);
        }

        String nickname = user.getUser().getNickname();
        if (nickname != null && !nickname.isEmpty()) {
            mNickName.setValue(nickname);
        } else {
            mNickName.setValue("请先登录");
        }

        String bio = user.getUser().getBio();
        if (bio != null && !bio.isEmpty()) {
            mBio.setValue(bio);
        } else {
            mBio.setValue("请编辑资料完善个人信息吧");
        }

        int fans = user.getFans();
        mFans.setValue(fans + " 粉丝");
        int follow = user.getFollow();
        mFollow.setValue(follow + " 关注");
        int medal = user.getMedal();
        mMedel.setValue(medal + " 徽章");
        Log.i("UserVM", "当前 nickname LiveData value = " + mNickName.getValue());

    }
}
