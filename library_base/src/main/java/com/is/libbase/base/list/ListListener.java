package com.is.libbase.base.list;


import com.is.network.bean.ResList;

public interface ListListener<T> {

    /**
     * 网络请求成功
     * @param video
     */
    void  OnLoadFinish(boolean isFirst,ResList<T> video);

    /**
     * 网络请求失败
     * @param statusCode 错误码
     */
    void OnLoadFail(int statusCode);
}
