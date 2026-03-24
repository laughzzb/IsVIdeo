package com.is.data_video.bean;

public class ReqVideoOperation {
    private int id;
    private String type;
    private int aid;

    //点赞
    public ReqVideoOperation(int id, String type) {
        this.id = id;
        this.type = type;
    }

    //取消点赞
    public ReqVideoOperation(int aid) {
        this.aid = aid;
    }

    //收藏
    public ReqVideoOperation(String type, int aid) {
        this.type = type;
        this.aid = aid;
    }
}
