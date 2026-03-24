package com.is.feature_user.ui.editinfo;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.is.feature_user.bean.ResUpload;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.evevntbus.MessageEvent;
import com.is.libbase.manager.UserInfo;
import com.is.network.bean.ResBase;

public class EditInfoViewModel extends BaseViewModel{

    private final EditInfoModel mModel;
    private MutableLiveData<String> mNickName = new MutableLiveData<>();//当前昵称
    private MutableLiveData<String> mBio = new MutableLiveData<>();//当前简介
    private MutableLiveData<String> mAvatarUrl = new MutableLiveData<>();//当前头像
    private MutableLiveData<EditUserAction> mAction = new MutableLiveData<>();

    public EditInfoViewModel() {
        mModel = new EditInfoModel();
        refresh();
    }



    /**
     * 刷新显示
     */
    private void refresh() {
        if (mModel.isLogin()){
            UserInfo userInfo = mModel.getUserInfo();
            mNickName.setValue(userInfo.getNickname());
            mBio.setValue(userInfo.getBio());
            mAvatarUrl.setValue(userInfo.getAvatar());
        }else {
            mNickName.setValue(null);
            mBio.setValue(null);
            mAvatarUrl.setValue(null);
        }
    }


    /**
     * 保存最新的用户信息
     */
    public void onSaveUserInfo(){
        if (isChange()){
            showLoading(true);
            String avatarUrl = mAvatarUrl.getValue();
            String nickName = mNickName.getValue();
            String bio = mBio.getValue();
            mModel.updataUserInfo(avatarUrl, nickName, bio, new IRequestCallback<ResBase>() {
                @Override
                public void onLoadFinish(ResBase dates) {
                    showToast(dates.getMsg());
                    showLoading(false);
                    //让user页面获得最新的用户信息做一个显示
                    MessageEvent.LoginStatusEvent.post(true);
                    mAction.setValue(EditUserAction.FINISH);
                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        }

    }

    /**
     * @return 资料是否有变化 如果有的，在按返回键的时候，会提示是否保存
     */
    public boolean isChange() {
        boolean change =false;
        UserInfo userInfo = mModel.getUserInfo();

        String avatarUrl = mAvatarUrl.getValue();
        //当头像不为空 并且和旧的资料不一致时，表示有改变
        if (avatarUrl !=null && !avatarUrl.equals(userInfo.getAvatar())){
            change =true;
        }
        String nickName = mNickName.getValue();
        if (nickName !=null &&  !nickName.equals(userInfo.getNickname())){
            change =true;
        }
        String bio = mBio.getValue();
        if (bio!=null && !bio.equals(userInfo.getBio())){
            change =true;
        }
        return change;
    }
    /**
     * 更换头像
     */
    public void onAvatarSelectClick() {
        mAction.setValue(EditUserAction.SHOW_AVATAR_SELECT_DIALOG);
    }

    public void uploadAvatar(Uri uri) {
        showLoading(true);
        mModel.updateFile(uri, new IRequestCallback<ResUpload>() {
            @Override
            public void onLoadFinish(ResUpload dates) {
                showToast("上传成功");
                showLoading(false);
                //更新当前头像信息（注意，这一步并没有保存，只是单纯的上传了图片，获取到新的图片地址而已）
                mAvatarUrl.setValue(dates.getUrl());
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

  //枚举
    public enum EditUserAction{
        FINISH,//关闭页面
        SHOW_AVATAR_SELECT_DIALOG
    }

    public MutableLiveData<String> getAvatarUrl() {
        return mAvatarUrl;
    }

    public MutableLiveData<String> getBio() {
        return mBio;
    }

    public MutableLiveData<String> getNickName() {
        return mNickName;
    }
    public MutableLiveData<EditUserAction> getAction() {
        return mAction;
    }
}
