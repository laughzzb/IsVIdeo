package com.is.feature_find.ui.topic;

import com.is.feature_find.api.FIndApiService;
import com.is.feature_find.api.FIndApiServiceProvider;
import com.is.feature_find.bean.ResFind;
import com.is.feature_find.bean.ResFindTopic;
import com.is.feature_find.bean.ResThemeData;
import com.is.feature_find.bean.ResTopic;
import com.is.libbase.base.IRequestCallback;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class TopicModel {

    public void requestTopicDatas(IRequestCallback<List<ResTopic>> callback) {
        Call<ResBase<List<ResTopic>>> call = FIndApiServiceProvider.getApiService().getTopic();
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<List<ResTopic>>>() {
            @Override
            public void onSuccess(ResBase<List<ResTopic>> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });
    }


}
