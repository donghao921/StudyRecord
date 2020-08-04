package com.dongh.baselibs.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;

/**
* BaseMvpPresenter
*@author dongh
*@time 2020/3/17
**/
public abstract class BaseMvpPresenter<V extends BaseMvpView>{
    protected Context context;
    private V baseView;

    public void attach(V baseView) {
        context = baseView.getContext();
        this.baseView = baseView;
    }

    public void detach() {
        context = null;
        baseView = null;
    }

    public Context getContext() {
        return context;
    }

    public V getBaseView() {
        return baseView;
    }

    public boolean isAttach() {
        return baseView != null;
    }

    public void showLoadingDialog() {
        if (baseView != null) {
            baseView.showLoadingDialog();
        }
    }

    public void dismissLoadDialog() {
        if (isAttach()) {
            baseView.dismissLoadingDialog();
        }
    }
}
