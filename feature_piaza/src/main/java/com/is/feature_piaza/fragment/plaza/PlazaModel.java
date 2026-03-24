package com.is.feature_piaza.fragment.plaza;

import com.is.feature_piaza.api.PlazaApiServiceProvider;
import com.is.feature_piaza.bean.ResPlaza;
import com.is.libbase.base.IRequestCallback;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class PlazaModel {


    public void requestDates(IRequestCallback<List<ResPlaza>> callback){
        //获取call
        Call<ResBase<List<ResPlaza>>> call = PlazaApiServiceProvider.getApiService().getPlaza();

        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<List<ResPlaza>>>() {
            @Override
            public void onSuccess(ResBase<List<ResPlaza>> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode,message);
            }
        });
    }



}
