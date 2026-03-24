package com.is.feature_user.ui.user;

import android.util.Log;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_user.BR;
import com.is.feature_user.R;
import com.is.feature_user.databinding.LayoutFragmentUserBinding;
import com.is.libbase.base.BaseFragment;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.evevntbus.MessageEvent;
import com.is.libbase.manager.UserManager;
import com.is.libbase.ui.dialog.YesOrNoDialog;
import com.is.libbase.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = ARoutePath.User.FRAGMENT_USER)
public class UserFragment extends BaseFragment<LayoutFragmentUserBinding, UserViewModel> {
    private static final String TAG = "UserFragment";



    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        StatusBarUtils.addStatusBarHeight2View(mViewDataBinding.getRoot(), mViewDataBinding.icSafe, mViewDataBinding.ivInstall);

        mViewModel.getAction().observe(this, userCenterAction -> {
            switch (userCenterAction) {
                case NAVIGATE_TO_LOGIN:
                    //跳转到登录界面
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_LOGIN).navigation();
                    break;
                case NAVIGATE_TO_EDIT_INFO:
                    //跳转到用户信息编辑
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_EDITINFO).navigation();
                    break;
                case NAVIGATE_TO_RECORD:
                    //跳转到播放记录
                    ARouter.getInstance().build(ARoutePath.Video.ACTIVITY_RECORD).navigation();
                    break;
                case SHOW_LOGOUT_DIALOG:
                    //显示是否退出登录
                    showLogoutDialog();
                    break;
                case NAVIGATE_TO_COLLECT:
                    //跳转到收藏界面
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_COLLECT).navigation();
                    break;
                case NAVIGATE_TO_SETTINGS:
                    //跳转到设置界面
                    ARouter.getInstance().build(ARoutePath.User.ACTIVITY_SETTINGS)
                            .navigation();
                    break;
            }
        });

    }


    /**
     * 是否退出登录的弹窗
     */
    private void showLogoutDialog() {
        if (getActivity() == null || isDetached()) return;

        YesOrNoDialog.showDialog(getActivity(), "提示", "是否退出登录",
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

    @Override
    protected UserViewModel getViewModel() {
        return new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_user;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    /**
     * 通过eventBus订阅 登录状态变化的消息
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent.LoginStatusEvent event) {
        boolean login = event.isLogin();
        Log.i(TAG, "onMessageEvent: event" + login);

        mViewModel.loadUSerInfo(login);

    }


    @Override
    public void onStart() {
        super.onStart();
        //确保页面活跃的时候再接收事件
        EventBus.getDefault().register(this);//注册eventBus

    }

    @Override
    public void onStop() {
        super.onStop();
        // 页面不活跃的时候取消事件接收
        if (EventBus.getDefault().isRegistered(this)) {
            // 修改：先移除粘性事件
            EventBus.getDefault().removeStickyEvent(MessageEvent.LoginStatusEvent.class);
            EventBus.getDefault().unregister(this);
        }
    }
}
