package com.is.feature_piaza.fragment.plaza;

import androidx.lifecycle.MutableLiveData;

import com.is.feature_piaza.bean.ResPlaza;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;

import java.util.List;

public class PlazaViewModel extends BaseViewModel implements IRequestCallback<List<ResPlaza>> {

    private final PlazaModel mModel;

    private MutableLiveData<List<ResPlaza>> mDates = new MutableLiveData<>();

    public PlazaViewModel() {
        mModel = new PlazaModel();
    }

    public void  requestDates(){
        mModel.requestDates(this);
    }

    @Override
    public void onLoadFinish(List<ResPlaza> dates) {
        mDates.setValue(dates);
    }

    public MutableLiveData<List<ResPlaza>> getDates() {
        return mDates;
    }

    @Override
    public void onLoadFail(int errorCode,String message) {
getErrorCode().setValue(errorCode);
    }
}
