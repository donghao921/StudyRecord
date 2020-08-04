package com.imdongh.mvpdemo01.base;

public abstract class BaseModel<T> {
    //
    protected String[] mParams;

    //
    public BaseModel params(String ... args) {
        mParams = args;
        return this;
    }

    // 添加Callback 并添加网络请求
    // 具体的数据请求由子类实现
    public abstract void execute(CallBack<T> callBack);

    //执行GET网络请求
    protected void requestGetApi(String url, CallBack<T> callBack){
        // 具体网络请求
    }

    // 执行POST网络请求
    protected void requestPostApi(String url, CallBack<T> callBack){
        // 具体网络请求
    }

}
