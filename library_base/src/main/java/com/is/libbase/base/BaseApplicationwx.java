//package com.is.libbase.base;
//
//import android.app.Application;
//import android.content.Context;
//import android.os.Handler;
//import android.util.Log;
//
//import com.alibaba.android.arouter.BuildConfig;
//import com.alibaba.android.arouter.facade.model.RouteMeta;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.is.library_wechat.WxApplication;
//
//import java.lang.reflect.Field;
//import java.util.Map;
//
//import me.jessyan.autosize.AutoSizeConfig;
//
///**
// * 当前工程中的Application基类
// */
//public class BaseApplication extends WxApplication {
//    private static final String TAG = "BaseApplication";
//
//    private static Application instance;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        instance = this;
//
//
//        //在调试模式下
//        if (BuildConfig.DEBUG){
//            ARouter.openLog();
//            ARouter.openDebug();
//        }
////         打印所有路由
//        new Handler().postDelayed(() -> {
//            printAllRoutes();
//        }, 2000);
//
//        //初始化ARouter
//        ARouter.init(this);
//
//        //AndroidAutoSize的参数初始化
//        AutoSizeConfig.getInstance().setCustomFragment(true);
//
//    }
//    private void printAllRoutes() {
//        try {
//            // 通过反射获取路由表
//            Class<?> warehouse = Class.forName("com.alibaba.android.arouter.core.Warehouse");
//            Field routesField = warehouse.getDeclaredField("routes");
//            routesField.setAccessible(true);
//            Map<String, RouteMeta> routes = (Map<String, RouteMeta>) routesField.get(null);
//
//            Log.d("ARouter", "=== 所有路由 ===");
//            for (String key : routes.keySet()) {
//                Log.d("ARouter", "Path: " + key + ", Meta: " + routes.get(key));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 使用Application生成了一个全局的context
//     * 注意不要滥用，否则会产生这两个问题
//     *1.把Application 当成是某个Activity上下文，与UI更新关联在一起，会引发错误
//     * 2.更容易获得到context,会增加项目的耦合性
//     * @return
//     */
//    public static Context getContext(){
//        return instance.getApplicationContext();//获取到这个app工程的上下文实例对象，跟随生命周期一直存在
//    }
//}
//
