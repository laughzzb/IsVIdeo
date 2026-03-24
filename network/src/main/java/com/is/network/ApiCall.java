package com.is.network;

import android.util.Log;

import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResBase;
import com.is.network.bean.ResList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    private static final String TAG = "ApiCall";

    public interface ApiCallback<T> {
        void onSuccess(T result);

        void onError(int errorCode, String message);
    }

    public interface ApiListCallback<T> {
        void onSuccess(ResList<T> result);

        void onError(int errorCode,String message);
    }

    /**
     * 发起异步请求，并且直接在这里处理服务端返回的状态
     * 如果成功，则通过onSuccess结果
     * 如果失败，，则通过onError
     *
     * @param call
     * @param callback
     * @param <T>
     */
    public static <T> void enqueue(Call<ResBase<T>> call, ApiCallback<ResBase<T>> callback) {

        //网络请求是否成功
        call.enqueue(new Callback<ResBase<T>>() {
            @Override
            public void onResponse(Call<ResBase<T>> call, Response<ResBase<T>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 1 && response.body() != null) {
                        Log.i(TAG, "onResponse: 数据处理成功");
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR,response.body().getMsg());
                    }

                } else {
                    callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL,"网络异常，请检查网络");
                }
            }


            @Override
            public void onFailure(Call<ResBase<T>> call, Throwable throwable) {
                callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL,"网络异常，请检查网络");
            }
        });

    }

    public static <T> void enqueueCommon(Call<T> call, ApiCallback<T> callback) {

        //网络请求是否成功
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                if (response.isSuccessful()) {
                    //请求成功，并拿到数据
                    callback.onSuccess(response.body());

                } else {
                    callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL,"网络异常，请检查网络");
                }
            }


            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL,"网络异常，请检查网络");
            }
        });

    }
    /**
     * 发起异步请求，获取列表型的数据，并且直接在这里处理服务端返回的状态
     * 如果成功，则通过onSuccess结果
     * 如果失败，，则通过onError
     * @param call
     * @param callback
     * @param <T>
     */
    public static <T> void enqueueList(Call<ResBase<ResList<T>>> call, ApiListCallback callback) {
        call.enqueue(new Callback<ResBase<ResList<T>>>() {
            @Override
            public void onResponse(Call<ResBase<ResList<T>>> call, Response<ResBase<ResList<T>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 1 && response.body() != null) {
                        Log.i(TAG, "onResponse: 数据处理成功");
                        ResBase<ResList<T>> body = response.body();
                        if (body.getData()!=null&& body.getData().getList().size()>0){
                            callback.onSuccess(response.body().getData());
                        }else {
                            callback.onError(ErrorStatusConfig.ERROR_STATUS_EMPTY,"当前没有更多的数据了");
                        }

                    } else {
                        callback.onError(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR,"服务器异常！");
                    }

                } else {
                    callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL,"网络异常，请检查网络");
                }
            }


            @Override
            public void onFailure(Call<ResBase<ResList<T>>> call, Throwable throwable) {
            callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL,"网络异常，请检查网络");
            }
        });

    }
}
