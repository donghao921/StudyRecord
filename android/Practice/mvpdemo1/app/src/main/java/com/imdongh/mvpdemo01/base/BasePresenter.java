package com.imdongh.mvpdemo01.base;

public class BasePresenter<V extends BaseView> {

    private V mView;

    public BasePresenter() {
        this.mView = mView;
    }

    public void attachView(V mView) {
        this.mView = mView;
    }

    public void detachView() {
        this.mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public V getView() {
        return mView;
    }

}
