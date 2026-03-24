package com.is.mediaPlayer.ui.record;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.is.libbase.base.BaseViewModel;
import com.is.libbase.base.IRequestCallback;
import com.is.mediaPlayer.db.VideoHistory;

import java.util.HashMap;
import java.util.List;

public class RecordViewModel extends BaseViewModel {
    private static final String TAG = "RecordViewModel";

    private final RecordModel mModel;
    private MutableLiveData<List<VideoHistory>>  mDatas = new MutableLiveData<>();
    //是否在编辑状态，默认为false
    private MutableLiveData<Boolean> mSelectStatus = new MutableLiveData<>(false);

    private HashMap<VideoHistory,Boolean> mSelectDelDatas ;//记录勾选过的数据

    public RecordViewModel() {
        mModel = new RecordModel();
    }
    //请求数据
    public void requestHistory(){
        showLoading(true);
        mModel.requestHistory(new IRequestCallback<List<VideoHistory>>() {
            @Override
            public void onLoadFinish(List<VideoHistory> dates) {
                //指定数据
                mDatas.setValue(dates);
                showLoading(false);
                Log.i(TAG, "onLoadFinish: datas"+dates.size());
            }

            @Override
            public void onLoadFail(int errorCode, String message) {
                showToast(message);
                showLoading(false);
                Log.i(TAG, "onLoadFail: errorCode = 3 没有数据");
            }
        });
    }

    /**
     * 多选操作
     */
    public void onSelectClick(){
        //如果不是勾选状态
        if (!mSelectStatus.getValue()){
            mSelectStatus.setValue(true);
            //开始编辑前初始化记录勾选数据的HasMap
            mSelectDelDatas = new HashMap<>();
        }else {
            mSelectStatus.setValue(false);
            showLoading(true);
            mModel.deleteByIds(mSelectDelDatas, new IRequestCallback<String>() {
                @Override
                public void onLoadFinish(String dates) {
                    showLoading(false);
                    showToast(dates);
                    requestHistory();//刷新ui
                }

                @Override
                public void onLoadFail(int errorCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
            mSelectDelDatas = null;
        }
    }

    public MutableLiveData<List<VideoHistory>> getDatas() {
        return mDatas;
    }

    public MutableLiveData<Boolean> getSelectStatus() {
        return mSelectStatus;
    }

    /**
     * 勾选或取消勾选了某条数据
     * @param history
     * @param isSelect
     */
    public void updateDelSelectDatas(VideoHistory history, boolean isSelect) {
        //取消选中
        if (mSelectDelDatas.containsKey(history)&& !isSelect){//是否包含这条数据
        mSelectDelDatas.remove(history);
            Log.i(TAG, "updateDelSelectDatas: 取消选中"+history.getVideoId());
        }
        //加入列表
        mSelectDelDatas.put(history,isSelect);
        Log.i(TAG, "updateDelSelectDatas: 加入选中"+history.getVideoId());

    }
}
