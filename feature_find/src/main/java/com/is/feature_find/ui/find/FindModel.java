package com.is.feature_find.ui.find;

import com.is.feature_find.api.FIndApiServiceProvider;
import com.is.feature_find.bean.ResFind;
import com.is.libbase.base.IRequestCallback;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;

import retrofit2.Call;

public class FindModel {
    public FindModel() {

    }

    public void loadFindData(IRequestCallback<ResFind> callback){

        Call<ResBase<ResFind>> call = FIndApiServiceProvider.getApiService().getFindData();

        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResFind>>() {
            @Override
            public void onSuccess(ResBase<ResFind> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode,message);
            }
        });
    }
}
