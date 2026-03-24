package com.is.video.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.is.video.MyApplication;

public class MainModel {

    /**
     * 保存用户同意的状态
     */
    public void savePrivacyAgreementStatus() {

        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("agreementStatus", Context.MODE_PRIVATE);
        //保存同意协议状态
        preferences.edit().putBoolean("agreement privacy",true).apply();
    }

    /**
     * 是否同意隐私协议
     * @return
     */
    public boolean getPrivacyAgreementStatus(){
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("agreementStatus", Context.MODE_PRIVATE);

        boolean privacy = preferences.getBoolean("agreement privacy", false);//没有存的话默认为false
        return privacy;
    }
}
