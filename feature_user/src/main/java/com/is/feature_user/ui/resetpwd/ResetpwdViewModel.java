package com.is.feature_user.ui.resetpwd;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.is.feature_user.bean.ResLogin;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.evevntbus.MessageEvent;
import com.is.libbase.manager.ResUser;
import com.is.network.bean.ResBase;

public class ResetpwdViewModel extends BaseViewModel {
    private static final String TAG = "ResetpwdViewModel";
    private final ResetpwdModel mModel;

    private MutableLiveData<String> mUserMobile = new MediatorLiveData<>();//用户输入的手机号
    private MutableLiveData<String> mCode = new MediatorLiveData<>();//用户输入的验证码
    private MutableLiveData<Boolean> mIsEnableLogin = new MediatorLiveData<>(false);//登录按钮是否可用,默认不可用
    private MutableLiveData<String> mGetVerticalCodeText = new MediatorLiveData<>("获取验证码");//获取验证码控件的显示文本
    private MutableLiveData<Boolean> mIsEnableSendCode = new MediatorLiveData<>(true);//获取验证码控件是否可用
    private MutableLiveData<Boolean> mLoginSuccess = new MediatorLiveData<>(false);//登录是否成功,默认不可用
    private CountDownTimer mDownTimer;//获取验证码倒计时
    private MutableLiveData<String> mPassword1 = new MutableLiveData<>();
    private MutableLiveData<String> mPassword2 = new MutableLiveData<>();

    public ResetpwdViewModel() {
        mModel = new ResetpwdModel();

        mUserMobile.setValue( mModel.getMobile());
    }



    /**
     * 发送验证码
     */
    public void sendCode() {

        String mobile = mUserMobile.getValue();
        if (mobile == null || mobile.length() != 11) {
            Log.i(TAG, "sendCode: 手机号不符合规则");
            showToast("请输入正确的手机号码");
            return;
        }

        if (mDownTimer != null) {
            mDownTimer.cancel();//防止重复点击时，未停止之前的倒计时
        }
        //禁用发送按钮
        mIsEnableSendCode.setValue(false);

        mDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onFinish() {
                //倒计时完成后
                mGetVerticalCodeText.setValue("获取验证码");
                //60s后允许发送验证码
                mIsEnableSendCode.setValue(true);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                //每一秒都会被触发 毫秒转换为秒
                int seconds = (int) (millisUntilFinished / 1000);
                mGetVerticalCodeText.setValue(seconds + "s");//更新倒计时的显示
            }
        }.start();
        //发送请求，让服务端发送验证码
        Log.i(TAG, "sendCode: ");
        showLoading(true);


        //发起获取验证码请求
        mModel.sendSmsCode(new IRequestCallback<ResBase<ResBase>>() {
            @Override
            public void onLoadFinish(ResBase<ResBase> dates) {
                showToast(dates.getMsg());//显示消息弹窗
                showLoading(false);
                onFinishPage();//关闭页面

            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showToast(message);
                showLoading(false);
            }
        });
    }

    /**
     * 提交
     */
    public void resetPassword() {
        String password1 = mPassword1.getValue();
        String password2 = mPassword2.getValue();
        String code = mCode.getValue();

        if (code == null && code.isEmpty()) {
            showToast("验证码不为空");
        }
        if (password1 == null && password1.isEmpty()) {
            showToast("密码不为空");
        }
        if (password2== null && password2.isEmpty()) {
            showToast("请确认密码");
        }
        if (!password1.equals(password2)){
            showToast("两次输入的密码不一致");
            return;
        }

        showLoading(true);
        mModel.resetPassword(password1, code, new IRequestCallback<ResBase<ResBase>>() {
            @Override
            public void onLoadFinish(ResBase<ResBase> dates) {
                showToast(dates.getMsg());
                showLoading(false);
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showToast(message);
                showLoading(false);
            }
        });


        String mobile = mUserMobile.getValue();
        mModel.mobileLogin(mobile, code, new IRequestCallback<ResBase<ResLogin>>() {
            @Override
            public void onLoadFinish(ResBase<ResLogin> dates) {
                Log.i(TAG, "onLoadFinish: token " + dates.getData());
                showLoading(false);
                showToast(dates.getMsg());

                int id = dates.getData().getId();
                getUserInfo(id);
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showToast(message);
                showLoading(false);
            }
        });

        Log.i(TAG, "login: 登录");

    }

    private void getUserInfo(int id) {
        showLoading(true);
        mModel.getUserInfo(String.valueOf(id), new IRequestCallback<ResBase<ResUser>>() {
            @Override
            public void onLoadFinish(ResBase<ResUser> dates) {
                showLoading(false);
                mLoginSuccess.setValue(true);
                //发送一个已登录的状态
                MessageEvent.LoginStatusEvent.post(true);
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    public MutableLiveData<Boolean> getIsEnableLogin() {
        return mIsEnableLogin;
    }

    public MutableLiveData<String> getCode() {
        return mCode;
    }

    public MutableLiveData<Boolean> getIsEnableSendCode() {
        return mIsEnableSendCode;
    }

    public MutableLiveData<String> getGetVerticalCodeText() {
        return mGetVerticalCodeText;
    }

    public MutableLiveData<String> getPassword2() {
        return mPassword2;
    }

    public MutableLiveData<String> getPassword1() {
        return mPassword1;
    }
    public MutableLiveData<String> getUserMobile() {
        return mUserMobile;
    }


    /**
     * 是否允许点击重置密码的按钮
     */
    public void updateEnableResetBtnStatus() {
        String password1 = mPassword1.getValue();
        String password2 = mPassword2.getValue();
        String code = mCode.getValue();

        if (code == null ||password1 == null || password2 == null ) {
           return;
        }
        //如果验证码不是四位数，并且两次输入的密码不一致，就不让点击重置按钮
        boolean isEnable = code.length()== 4 && password1.equals(password2);
        mIsEnableLogin.setValue(isEnable);
    }
}
