package com.is.feature_user.ui.about;

import com.is.libbase.utils.VersionUtils;

public class AboutMeModel {

    /**
     * 获取应用的版本名称
     */
    public String getVersionName(){
        String versionName = VersionUtils.getVersionName();
        return versionName;
    }

    /**
     * 获取应用的版本代码
     * @return
     */
    public int gerVersionCode(){
        int versionCode = VersionUtils.getVersionCode();
        return versionCode;
    }
}
