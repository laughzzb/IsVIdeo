package com.is.libbase.base;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public MediatorLiveData<Integer> mErrorCode = new MediatorLiveData<>();
    public MediatorLiveData<Integer> getErrorCode() {
        return mErrorCode;
    }

    //如果toastText发生了变化，表示需要进行弹窗显示
    private MutableLiveData<String> mToastText = new MutableLiveData<>();

    //是否显示加载样式
    private MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFinish = new MutableLiveData<>();//是否需要关闭当前页面
    private MutableLiveData<Boolean> mStartLoginAction = new MutableLiveData<>();

    public MutableLiveData<Boolean> getStartLoginAction() {
        return mStartLoginAction;
    }

    public void startLogin(){
        // 清除旧值，避免重复触发
        mStartLoginAction.setValue(true);
    };


    public MutableLiveData<Boolean> getFinish() {
        return mFinish;
    }

    /**
     * 关闭页面
     */
    public void onFinishPage(){
        mFinish.setValue(true);
    }

    /**
     * 显示吐司弹窗
     * @param text
     */
    public void showToast(String text){
        if (text ==null || text.equals("")){
            return;
        }
        mToastText.setValue(null);//解决公用viewModel多次弹窗问题
        mToastText.setValue(text);


    }
    public MutableLiveData<String> getToastText() {
        return mToastText;
    }

    /**
     * 是否显示弹窗
     * @param b
     */
    public void showLoading(boolean b){
        // 避免重复设置相同的值导致不必要的通知
        Boolean currentValue = mShowLoading.getValue();
        if (currentValue == null || currentValue != b) {
            mShowLoading.setValue(b);
        }
        mShowLoading.setValue(b);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return mShowLoading;
    }
}
