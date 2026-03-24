package com.is.libbase.base;

import java.util.List;

/**
 * model和ViewModel通讯的接口回调
 */
public interface IRequestCallback<T> {
    void onLoadFinish(T dates);
    void onLoadFail(int errorCode,String message);
}
