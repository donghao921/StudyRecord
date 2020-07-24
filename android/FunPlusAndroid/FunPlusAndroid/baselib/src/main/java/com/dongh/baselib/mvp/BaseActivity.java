package com.dongh.baselib.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public abstract class BaseActivity<T extends BasePresenter> extends FragmentActivity implements BaseView{
    // 该Activity实例，命名为context是因为大部分方法都只需要context，写成context使用更方便
    protected BaseActivity context = null;
    protected T mPresenter;

    // 该Activity的界面，即contentView
    protected View view = null;

    // 布局解释器
    protected LayoutInflater inflater = null;

    // Fragment管理器
    protected FragmentManager fragmentManager = null;

    // 用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
    protected Intent intent = null;

    private boolean isAlive = false;
    private boolean isRunning = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = BaseActivity.this;
        isAlive =true;
        fragmentManager = getSupportFragmentManager();
        inflater = getLayoutInflater();

        //
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
