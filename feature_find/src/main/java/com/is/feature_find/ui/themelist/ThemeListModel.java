package com.is.feature_find.ui.themelist;

import com.is.feature_find.api.FIndApiService;
import com.is.feature_find.api.FIndApiServiceProvider;
import com.is.feature_find.bean.ResThemeData;
import com.is.libbase.base.IRequestCallback;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class ThemeListModel {

    public void requestData(IRequestCallback<List<ResThemeData>> callback){
        Call<ResBase<List<ResThemeData>>> call = FIndApiServiceProvider.getApiService().getAnchor();
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<List<ResThemeData>>>() {
            @Override
            public void onSuccess(ResBase<List<ResThemeData>> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }


}
