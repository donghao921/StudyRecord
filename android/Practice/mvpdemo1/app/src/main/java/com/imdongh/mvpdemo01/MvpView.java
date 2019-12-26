package com.imdongh.mvpdemo01;

import com.imdongh.mvpdemo01.base.BaseView;

public interface MvpView extends BaseView {

    /**
     * 当数据请求成功后，调用此接口显示数据
     */
    void showData(String data);

    /**
     * 当数据请求失败后，调用此接口提示
     * @param message
     */
    void showFailureMessage(String message);

    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage();
}
