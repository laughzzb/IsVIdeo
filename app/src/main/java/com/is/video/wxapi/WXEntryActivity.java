//package com.is.video.wxapi;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import com.is.libbase.base.BaseApplication;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//
////接收结果的页面
//public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
//    private static final String TAG = "WXEntryActivity";
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        IWXAPI api = BaseApplication.getApi();
//        api.handleIntent(getIntent(),this);
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//    /**
//     * 我们的app发送（分享，登录，支付）等等这些能力请求到微信，微信的处理结果会在这里被响应
//     */
//    @Override
//    public void onResp(BaseResp baseResp) {
//        Log.i(TAG, "onResp: 接收到结果");
//        switch (baseResp.errCode){
//            case BaseResp.ErrCode.ERR_OK:
//                //分析成功处理
//                Log.i(TAG, "onResp:分享成功 ");
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                Log.i(TAG, "onResp: 用户取消操作");
//                //用户取消操作
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                //分享被拒绝
//                Log.i(TAG, "onResp: 分享被拒绝");
//                break;
//            default:
//                //其他错误类型
//                Log.i(TAG, "onResp: 其他错误："+baseResp.errCode);
//                break;
//        }
//    }
//}
