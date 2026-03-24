package com.is.feature_find.ui.find;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.is.data_video.bean.ResFindCategory;
import com.is.feature_find.bean.ResFind;
import com.is.feature_find.bean.ResFindAnchor;
import com.is.feature_find.bean.ResFindTopic;
import com.is.feature_find.bean.ResTopic;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;

import java.util.List;

public class FindViewModel extends BaseViewModel implements IRequestCallback<ResFind> {

    private static final String TAG = "FindViewModel";
    private final FindModel mModel;
    private MutableLiveData<List<ResFindCategory>> mCategory = new MutableLiveData<>();
    private MutableLiveData<List<ResFindAnchor>> mAnchor = new MutableLiveData<>();
    private MutableLiveData<List<ResFindTopic>> mTopic = new MutableLiveData<>();
    private MutableLiveData<FindAction> mAction = new MutableLiveData<>();

    public FindViewModel() {
        mModel = new FindModel();
    }

    public void loadFindData() {
        mModel.loadFindData(this);
        Log.i(TAG, "loadFindData: 请求发现页数据");
    }

    @Override
    public void onLoadFinish(ResFind dates) {
        Log.i(TAG, "onLoadFinish: " + dates.getCategory().size());
        //获取到数据，更细到mCategory
        mCategory.setValue(dates.getCategory());

        mAnchor.setValue(dates.getAnchor());

        mTopic.setValue(dates.getTopic());

    }

    @Override
    public void onLoadFail(int errorCode, String message) {
        Log.i(TAG, "onLoadFinish : errorCode " + errorCode);
    }

    public MutableLiveData<List<ResFindCategory>> getCategory() {
        return mCategory;
    }

    public MutableLiveData<List<ResFindAnchor>> getAnchor() {
        return mAnchor;
    }

    public MutableLiveData<List<ResFindTopic>> getTopic() {
        return mTopic;
    }

    public void startThemeListActivity() {
        //跳转到主题播单
        mAction.setValue(FindAction.NAVIGATION_TO_THEME_LIST);
    }

    public MutableLiveData<FindAction> getAction() {
        return mAction;
    }

    public void startTopicActivity() {
        //跳转到话题广场
        mAction.setValue(FindAction.NAVIGATION_TO_TOPIC);
    }


    public enum FindAction {
        NAVIGATION_TO_THEME_LIST, //跳转到主题播单
        NAVIGATION_TO_TOPIC,//跳转到话题广场
    }
}
