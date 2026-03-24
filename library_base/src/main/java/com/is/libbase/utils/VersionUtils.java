package com.is.libbase.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.is.libbase.base.BaseApplication;

public class VersionUtils {

    /**
     * 获取应用的版本名称
     */
    public static String getVersionName() {
        try {
            Context context = BaseApplication.getContext();
            //获取PackageManager 实例
            PackageManager packageManager = context.getPackageManager();
            //获取当前应用的包信息
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            //返回版本名称
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取应用的版本代码
     */
    public static int getVersionCode() {
        Context context = BaseApplication.getContext();
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return (int) packageInfo.getLongVersionCode();
            } else {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }


    }
}
