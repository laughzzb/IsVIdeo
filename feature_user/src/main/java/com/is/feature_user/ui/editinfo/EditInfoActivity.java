package com.is.feature_user.ui.editinfo;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_user.BR;
import com.is.feature_user.R;
import com.is.feature_user.config.UserConfig;
import com.is.feature_user.databinding.ActivityEditInfoBinding;
import com.is.libbase.adapter.CommonBindingAdapter;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.ui.dialog.YesOrNoDialog;
import com.is.libbase.utils.StatusBarUtils;

@Route(path = ARoutePath.User.ACTIVITY_EDITINFO)
public class EditInfoActivity extends BaseActivity<ActivityEditInfoBinding, EditInfoViewModel> {

    private ActivityResultLauncher<String> pickImageLauncher;


    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());

        mViewModel.getAction().observe(this, new Observer<EditInfoViewModel.EditUserAction>() {
            @Override
            public void onChanged(EditInfoViewModel.EditUserAction editUserAction) {
                switch (editUserAction) {
                    case SHOW_AVATAR_SELECT_DIALOG:
                        //显示弹窗
                        showAvatarSelectDialog();
                        break;
                    case FINISH:
                        EditInfoActivity.super.finish();//调用父类finish，直接关闭
                        break;
                }
            }
        });

//        初始化 ActivityResultLauncher
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null){
                            CommonBindingAdapter.loadCircleImage(mViewDataBinding.ivAvatar, uri);
                        mViewModel.uploadAvatar(uri);
                        }
                    }
                }

        );
    }

    @Override
    protected void initData() {

    }

    public void finish() {
        if (mViewModel.isChange()) {
            YesOrNoDialog.showDialog(this, "提示", "是否保存更新", new YesOrNoDialog.Callback() {
                @Override
                public void onConfirm() {
                    //提交保存，提交成功后会再执行super.finfish
                    mViewModel.onSaveUserInfo();
                }

                public void onCancel() {
                    //直接关闭
                    EditInfoActivity.super.finish();
                }
            });
        } else {
            super.finish();//一定要调用父类的finish，否则就是调用当前类中的finish
        }
    }


    //显示选择头像的弹窗（弹窗，或者是相机拍摄）
    private void showAvatarSelectDialog() {
        PictureSelectDialog pictureSelectDialog = PictureSelectDialog.newInstance();

        pictureSelectDialog.setOnItemClickListener(new PictureSelectDialog.OnItemClickListener() {
            @Override
            public void onCameraClick() {
                //请求相机权限，如果有权限直接启动相机
                if (allPermissionsGranted()) {
                    startCamera();
                } else{
                    ActivityCompat.requestPermissions(EditInfoActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);

                }
            }

            @Override
            public void onAlbumClick() {
                openGallery();
            }
        });

        pictureSelectDialog.show(getSupportFragmentManager(), "PictureSelectDialog");
    }

    /**
     * 打开相册
     */
    private void openGallery() {
        pickImageLauncher.launch("image/*");
    }

    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};

    /**
     * 检查是否已经获取所有权限
     * @return
     */
    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    // 权限请求结果回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                mViewModel.showToast("权限获取失败，请在系统设置手动授权");
                finish();
            }
        }
    }

    private void startCamera(){
        //需要实现接收回传值：
        //1.navigation（this，100）需要指定请求码2.在onActivityResult中获取结果
        ARouter.getInstance().build(ARoutePath.User.ACTIVITY_CAMERA).navigation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果结果码与拍摄照片相同
        if (UserConfig.Camera_CAPTURE.CAPTURE_REQUEST == resultCode){
            //获取照片拍摄结果
            Uri uri = data.getParcelableExtra(UserConfig.Camera_CAPTURE.KEY_CAPTURE_REQUEST);
            mViewModel.uploadAvatar(uri);
        }
    }



    @Override
    protected EditInfoViewModel getViewModel() {
        return new ViewModelProvider(this).get(EditInfoViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}