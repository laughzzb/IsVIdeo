package com.is.libbase.base.list;

import androidx.lifecycle.MediatorLiveData;

import com.is.libbase.base.BaseViewModel;
import com.is.network.Config.ErrorStatusConfig;
import com.is.network.bean.ResList;

import java.util.List;

public class BaseListViewModel<T,V extends BaseListModel> extends BaseViewModel implements ListListener<T>{
    public V mModel;
    //列表需要使用到的数据
    public MediatorLiveData<ResList<T>> mDates = new MediatorLiveData<>();

    //是否还能接着加载,默认是true
    public MediatorLiveData<Boolean> mIsLoadMore = new MediatorLiveData<>(true);


    public BaseListViewModel(V model) {
        this.mModel = model;
        mModel.setListener(this);
    }

    public void requestDates(boolean isFirst){
        if (isFirst){
            mIsLoadMore.setValue(true);
        }
        mModel.requestDates(isFirst);
    }

    public MediatorLiveData<ResList<T>> getDates() {
        return mDates;
    }

    public MediatorLiveData<Boolean> getIsLoadMore() {
        return mIsLoadMore;
    }

    @Override
    public void OnLoadFinish(boolean isFirst, ResList<T> dates) {
        if (isFirst){
            //首次加载/刷新
            mDates.setValue(dates);
        }else {
            //分页加载
            ResList<T> value = mDates.getValue();
            List<T>  list = value.getList();
            list.addAll(dates.getList());
            mDates.setValue(value);
        }
        //当前列表的总数
        int count = dates.getCount();
        if (mDates.getValue().getList().size()>= count){
            mIsLoadMore.setValue(false);
        }



    }

    @Override
    public void OnLoadFail(int statusCode) {

        mErrorCode.setValue(statusCode);
        //为空就不能再加载了
        if (statusCode == ErrorStatusConfig.ERROR_STATUS_EMPTY){
            mIsLoadMore.setValue(false);
        }
    }
}
