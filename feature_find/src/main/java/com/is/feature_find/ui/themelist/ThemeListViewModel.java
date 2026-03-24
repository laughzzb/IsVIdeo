package com.is.feature_find.ui.themelist;

import androidx.lifecycle.MutableLiveData;

import com.is.feature_find.bean.ResThemeData;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;

import java.util.List;

public class ThemeListViewModel extends BaseViewModel {

    private final ThemeListModel mModel;



    private MutableLiveData<List<ResThemeData>> mDatas = new MutableLiveData<>();

    public ThemeListViewModel(){
        mModel = new ThemeListModel();
    }



    public void requestData(){
        showLoading(true);
        mModel.requestData(new IRequestCallback<List<ResThemeData>>() {
            @Override
            public void onLoadFinish(List<ResThemeData> dates) {
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
    public MutableLiveData<List<ResThemeData>> getDatas() {
        return mDatas;
    }
}
