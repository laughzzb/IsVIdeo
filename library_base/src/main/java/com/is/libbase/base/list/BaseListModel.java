package com.is.libbase.base.list;

public abstract class BaseListModel {
    protected int mPage = 1;//当前请求的页数
    protected final int mLimit = 10;//每页多少条数据
    protected ListListener mListener;

    public void setListener(ListListener listener) {
        this.mListener = listener;
    }

    /**
     * 分页加载网络数据
     * @param isFirst 是否第一次加载
     */
    public abstract void requestDates(boolean isFirst);
}
