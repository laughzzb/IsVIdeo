package com.is.feature_find.ui.categorydetail;

import com.is.data_video.bean.ResFindCategory;
import com.is.feature_find.api.FIndApiServiceProvider;
import com.is.feature_find.bean.ResCategoryDetail;
import com.is.libbase.base.IRequestCallback;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;

import retrofit2.Call;

public class CategoryDetailModel {

    /**
     * 请求分类详情数据
     *
     * @param id
     */
    public void requestDatas(int id, IRequestCallback<ResFindCategory> callback) {

        Call<ResBase<ResCategoryDetail>> call = FIndApiServiceProvider.getApiService().getCategoryDetail(id);
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<ResCategoryDetail>>() {
            @Override
            public void onSuccess(ResBase<ResCategoryDetail> result) {
                //获取数据
                ResFindCategory info =result.getData().getInfo();
                callback.onLoadFinish(info);

            }

            @Override
            public void onError(int errorCode, String meesage) {
                callback.onLoadFail(errorCode, meesage);
            }
        });
    }
}
