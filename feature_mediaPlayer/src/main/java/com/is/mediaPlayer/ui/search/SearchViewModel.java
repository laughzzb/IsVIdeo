package com.is.mediaPlayer.ui.search;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.is.data_video.bean.ResVideoDetail;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;

import java.util.List;

public class SearchViewModel extends BaseViewModel {


    private final SearchModel mModel;
    //搜索关键词
    private MutableLiveData<String> mSearchKeyword = new MutableLiveData<>();
    private MutableLiveData<List<ResVideoDetail.ArchivesInfoBean>> mDatas = new MutableLiveData<>();

    //是否显示清除搜索内容的按钮 默认不显示
    private MutableLiveData<Integer> mShowCleanButton = new MutableLiveData<>(View.INVISIBLE);

    public void setDatas(MutableLiveData<List<ResVideoDetail.ArchivesInfoBean>> mDatas) {
        this.mDatas = mDatas;
    }


    public MutableLiveData<List<ResVideoDetail.ArchivesInfoBean>> getDatas() {
        return mDatas;
    }

    public MutableLiveData<String> getSearchKeyword() {
        return mSearchKeyword;
    }

    public MutableLiveData<Integer> getShowCleanButton() {
        return mShowCleanButton;
    }

    public SearchViewModel() {
        mModel = new SearchModel();
    }


    /**
     * 清除关键词
     */
    public void cleanSearchKeyword() {
        mSearchKeyword.setValue("");
    }

    /**
     *
     */
    public void searchByKeyword(){
        String keyword = mSearchKeyword.getValue();
        if (keyword !=null && keyword.length()>0){
            showLoading(true);
            mModel.searchByKeyword(keyword, new IRequestCallback<List<ResVideoDetail.ArchivesInfoBean>>() {
                @Override
                public void onLoadFinish(List<ResVideoDetail.ArchivesInfoBean> dates) {
                    showLoading(false);
                    mDatas.setValue(dates);
                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        }else {
            showToast("搜索词不能为空");
        }
    }
    public void update(boolean isShow) {
        mShowCleanButton.setValue(isShow ? View.VISIBLE : View.INVISIBLE);
    }
}
