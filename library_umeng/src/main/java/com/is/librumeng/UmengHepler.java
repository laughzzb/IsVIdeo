package com.is.librumeng;

import android.content.Context;
import android.util.Log;

import com.umeng.commonsdk.BuildConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class UmengHepler {
    private static final String TAG = "UmengHepler";
    
    public static void  init(Context context){
        Log.i(TAG, "init: 正在初始化友盟。。。。");
        //log日志开关
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);//根据是否为debug状态调整
        UMConfigure.preInit(context,"5a12384aa40fa3551f0001d1", "isVideo");
        //友盟正式初始化
        UMConfigure.init(context, "5a12384aa40fa3551f0001d1", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");


        // 微信设置
        PlatformConfig.setWeixin("wx9cd1208e8ddd6deb","3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setWXFileProvider("com.is.video.fileprovider");
    }
}
