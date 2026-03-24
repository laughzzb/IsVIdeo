package com.is.video.ui;

import com.is.libbase.base.BaseViewModel;

public class MainVIewModel extends BaseViewModel {

    private final MainModel mMdodel;

    public MainVIewModel() {

        mMdodel = new MainModel();
    }

    /**
     * 保存用户同意的状态
     */
    public void savePrivacyAgreementStatus(){
        mMdodel.savePrivacyAgreementStatus();
    }
    public boolean getPrivacyAgreementStatus() {
        return mMdodel.getPrivacyAgreementStatus();
    }
}
