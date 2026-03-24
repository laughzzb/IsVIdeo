package com.is.mediaPlayer.ui.record;

import com.is.libbase.base.BaseApplication;
import com.is.libbase.base.IRequestCallback;
import com.is.libbase.manager.UserManager;
import com.is.mediaPlayer.db.VideoHistory;
import com.is.mediaPlayer.db.VideoHistoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RecordModel {
    public void requestHistory(IRequestCallback<List<VideoHistory>>  callback) {
        VideoHistoryRepository repository = new VideoHistoryRepository(BaseApplication.getContext());
        String userId ="0";
        if (UserManager.getInstance().isLogin()){
            userId = UserManager.getInstance().getUSerInfo().getUser().getId();
        }
        //查询
        repository.query(userId,callback);
    }

    public void deleteByIds(HashMap<VideoHistory, Boolean> mSelectDelDatas,IRequestCallback<String> callback) {
        VideoHistoryRepository repository = new VideoHistoryRepository(BaseApplication.getContext());
        String userId ="0";
        if (UserManager.getInstance().isLogin()){
            userId = UserManager.getInstance().getUSerInfo().getUser().getId();
        }

        List<Integer> ids = new ArrayList<>();
        Set<VideoHistory> histories = mSelectDelDatas.keySet();
        for (VideoHistory history :histories) {
            ids.add(history.getVideoId());
        }
        repository.deleteByIds(userId,ids,callback);
    }
}
