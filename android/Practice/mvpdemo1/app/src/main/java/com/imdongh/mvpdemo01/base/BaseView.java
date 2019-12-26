package com.imdongh.mvpdemo01.base;

import android.content.Context;

public interface BaseView {
    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 当数据请求成功后，调用此接口显示数据
     */
//    void showData(String data);

    /**
     * 当数据请求失败后，调用此接口提示
     * @param message
     */
//    void showFailureMessage(String message);

    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage();

    /**
     *
     */
    void showToast(String msg);

    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();
}
