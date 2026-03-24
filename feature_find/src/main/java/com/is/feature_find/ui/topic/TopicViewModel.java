package com.is.feature_find.ui.topic;

import androidx.lifecycle.MutableLiveData;

import com.is.feature_find.bean.ResFindTopic;
import com.is.feature_find.bean.ResTopic;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;

import java.util.List;

public class TopicViewModel extends BaseViewModel {

    private final TopicModel mModel;
    private MutableLiveData<List<ResTopic>> mDatas = new MutableLiveData<>();

    public MutableLiveData<List<ResTopic>> getDatas() {
        return mDatas;
    }

    public TopicViewModel() {
        mModel = new TopicModel();
    }

    public void requestTopicDatas(){
        showLoading(true);
        mModel.requestTopicDatas(new IRequestCallback<List<ResTopic>>() {
            @Override
            public void onLoadFinish(List<ResTopic> dates) {
                showLoading(false);
                mDatas.setValue(dates);
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }


}
