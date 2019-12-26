package com.imdongh.mvpdemo01.base;

public interface CallBack<T> {
    /**
     * 数据请求成功
     * @param data
     */
    void onSucc(T data);

    /**
     * 使用网络API接口请求方式时，虽然请求成功但由于 message 的原因无法正常返回数据
     * @param message
     */
    void onFail(String message);

    /**
     * 请求数据失败，指在请求网络API时，出现网络连接，缺少权限，内存泄漏等问题导致无法连接到请求数据源
     * @param error
     */
    void onError(String error);

    /**
     * 当请求数据结束时，无论请求是成功，失败还是抛出异常都会执行此方法给用户处理
     * 通常做网络请求时可以在此处隐藏"正在加载"等显示控件
     */
    void onComplete();
}
