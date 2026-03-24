package com.is.feature_user.ui.about;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.is.libbase.base.BaseViewModel;


public class AboutMeViewModel extends BaseViewModel{

    private final AboutMeModel mModel;
    private MutableLiveData<String> mVersionLabel = new MediatorLiveData<>();

    public MutableLiveData<String> getVersionLabel() {
        return mVersionLabel;
    }

    public AboutMeViewModel() {
        mModel = new AboutMeModel();
        int versionCode = mModel.gerVersionCode();
        String versionName = mModel.getVersionName();


        mVersionLabel.setValue("版本信息：v"+versionName+"-"+versionCode);
    }

}
