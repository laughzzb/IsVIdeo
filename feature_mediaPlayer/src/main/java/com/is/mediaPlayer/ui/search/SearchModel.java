package com.is.mediaPlayer.ui.search;

import com.is.data_video.bean.ResVideoDetail;
import com.is.libbase.base.IRequestCallback;
import com.is.mediaPlayer.api.MediaPlayerApiServiceProvider;
import com.is.network.ApiCall;
import com.is.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class SearchModel {

    public void searchByKeyword(String keyword,IRequestCallback<List<ResVideoDetail.ArchivesInfoBean>> callback){
        Call<ResBase<List<ResVideoDetail.ArchivesInfoBean>>> call = MediaPlayerApiServiceProvider.getApiService().search(keyword);
        ApiCall.enqueue(call, new ApiCall.ApiCallback<ResBase<List<ResVideoDetail.ArchivesInfoBean>>>() {
            @Override
            public void onSuccess(ResBase<List<ResVideoDetail.ArchivesInfoBean>> result) {
                callback.onLoadFinish(result.getData());
            }

            @Override
            public void onError(int errorCode, String message) {
                callback.onLoadFail(errorCode, message);
            }
        });

    }
}
