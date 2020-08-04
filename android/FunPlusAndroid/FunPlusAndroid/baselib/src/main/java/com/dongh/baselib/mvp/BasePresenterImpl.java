package com.dongh.baselib.mvp;

import android.content.Context;
import android.util.Log;

import rx.Subscription;

/**
*BasePresenter
*@author dongh
*@time 2019-12-26
**/
public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    public Subscription mSubscription;
    protected Context context;
    protected V mView;

    public BasePresenterImpl(V mView) {
        this.mView = mView;
    }

    @Override
    public void start() {
        attach(mView);
        mView.setPresenter(this);
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        if (isAttach()) {
            detach();
        }
    }

    /**
     * 绑定view，一般在View的OnCreate或onResume中调用
     * @param mView
     */
    public void attach(V mView) {
        this.mView = mView;
    }

    /**
     * 解绑 view ，一般在 View 的 onDestory 或 onStop 中调用
     */
    public void detach() {
        mView = null;
    }

    /**
     * 判断View对象是否存在
     * @return
     */
    public boolean isAttach() {
        return mView != null;
    }

}
