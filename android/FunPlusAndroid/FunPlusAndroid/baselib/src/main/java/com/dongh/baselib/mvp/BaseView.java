package com.dongh.baselib.mvp;

import android.content.Context;

/**
*BaseView
*@author dongh
*@time 2019-12-26
**/
public interface BaseView<T> {

    /**
     * 设置 presenter
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     */
    void onError(Throwable throwable);


}
