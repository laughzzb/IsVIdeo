package com.is.feature_user.ui.account;

import androidx.lifecycle.MutableLiveData;

import com.is.libbase.base.BaseViewModel;

public class AccountViewModel extends BaseViewModel {

    private final AccountModel mModel;
    private MutableLiveData<String> mMobile = new MutableLiveData<>();

    public MutableLiveData<String> getMobile() {
        return mMobile;
    }

    public AccountViewModel() {
        mModel = new AccountModel();

        String mobile = mModel.getMobile();
        mMobile.setValue(mobile);
    }
    public void onAccountBindClick(){
        showToast("无法换绑");
    }


}
