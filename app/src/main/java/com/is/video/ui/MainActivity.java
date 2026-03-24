package com.is.video.ui;

import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_user.ui.Agreement.InitAgreementDialog;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseApplication;
import com.is.libbase.config.ARoutePath;
import com.is.librumeng.UmengHepler;
import com.is.video.BR;
import com.is.video.R;
import com.is.video.databinding.ActivityMainBinding;
import com.umeng.commonsdk.UMConfigure;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainVIewModel> {
    private static final String TAG = "MainActivity";
    private long lastBackPressedTime = 0; // 记录上次点击返回键的时间
    private static final int DOUBLE_PRESS_INTERVAL = 2000; // 双击间隔（毫秒）
    private Toast exitToast; // 避免重复创建Toast



    @Override
    protected MainVIewModel getViewModel() {
        return new ViewModelProvider(this).get(MainVIewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(ARoutePath.Home.FRAGMENT_HOME).navigation();
        replaceFragment(homeFragment);

        mViewDataBinding.rbBottomNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                Fragment targetFragment = null;
                if (checkedId == R.id.rb_home) {
                    targetFragment = (Fragment) ARouter.getInstance()
                            .build(ARoutePath.Home.FRAGMENT_HOME).navigation();
                } else if (checkedId == R.id.rb_find) {
                    targetFragment = (Fragment) ARouter.getInstance()
                            .build(ARoutePath.Find.FRAGMENT_FIND).navigation();
                } else if (checkedId == R.id.rb_plaza) {
                    targetFragment = (Fragment) ARouter.getInstance()
                            .build(ARoutePath.Plaza.FRAGMENT_PLAZA).navigation();
                } else if (checkedId == R.id.rb_mine) {
                    targetFragment = (Fragment) ARouter.getInstance()
                            .build(ARoutePath.User.FRAGMENT_USER).navigation();
                }

                if (targetFragment != null) {
                    replaceFragment(targetFragment);
                }
            }
        });

        if (!mViewModel.getPrivacyAgreementStatus()) {
            InitAgreementDialog dialog = new InitAgreementDialog();
            dialog.setCallback(new InitAgreementDialog.AgreementCallback() {
                @Override
                public void onAgreement() {
                    mViewModel.savePrivacyAgreementStatus();
                    //必须用户同意了后才能做初始化获取用户数据

                    UmengHepler.init(BaseApplication.getContext());
                }

                @Override
                public void onExit() {


                    finish();//关闭app
                }
            });
            dialog.show(getSupportFragmentManager(), "initagreement");
        }


        // 创建并注册返回键回调
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastBackPressedTime < DOUBLE_PRESS_INTERVAL) {
                    // 双击退出
                    if (exitToast != null) {
                        exitToast.cancel();
                    }

                    // 优雅退出应用
                    finishAffinity();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        finishAndRemoveTask();
                    }
                    System.exit(0);
                } else {
                    // 第一次点击
                    lastBackPressedTime = currentTime;
                    showExitToast();

                    // 添加延迟重置逻辑（可选）
                    getWindow().getDecorView().postDelayed(() -> {
                        // 重置时间，防止用户等待过久后再次点击无效
                        lastBackPressedTime = 0;
                    }, DOUBLE_PRESS_INTERVAL + 500); // 比双击间隔多500ms
                }
            }
        });
    }

    private void showExitToast() {
        if (exitToast != null) {
            exitToast.cancel();
        }
        exitToast = Toast.makeText(
                this,
                R.string.press_again_to_exit,
                Toast.LENGTH_SHORT
        );
        exitToast.show();
    }

    private int replaceFragment(Fragment homeFragment) {
        return getSupportFragmentManager().beginTransaction().replace(com.is.mediaPlayer.R.id.fcv, homeFragment).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 清理 Toast
        if (exitToast != null) {
            exitToast.cancel();
            exitToast = null;
        }

    }
}

