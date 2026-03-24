package com.is.feature_find.ui.categorydetail;

import androidx.lifecycle.MutableLiveData;

import com.is.data_video.bean.ResFindCategory;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;

public class CategoryDetailViewModel extends BaseViewModel{
    private final CategoryDetailModel mModel;
    private MutableLiveData<ResFindCategory> mCategoryDetail = new MutableLiveData<>();

    //显示数据：44万人参与·512万人浏览
    private MutableLiveData<String> mPepoleCount = new MutableLiveData<>();

    public CategoryDetailViewModel() {
        mModel = new CategoryDetailModel();
    }


    /**
     * 请求分类详情数据
     *
     * @param id
     */
    public void requestDatas(int id) {

        mModel.requestDatas(id, new IRequestCallback<ResFindCategory>() {
            @Override
            public void onLoadFinish(ResFindCategory dates) {
                mCategoryDetail.setValue(dates);
                mPepoleCount.setValue(String.format("%s万人参与·%s万人浏览", dates.getPeople(), dates.getBrowse()));
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showToast(message);
            }
        });
    }

    public MutableLiveData<ResFindCategory> getCategoryDetail() {
        return mCategoryDetail;
    }

    public MutableLiveData<String> getPepoleCount() {
        return mPepoleCount;
    }



}
