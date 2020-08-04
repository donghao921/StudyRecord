package com.dongh.baselib.mvp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dongh.baselib.R;

public class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView<T>{
    private T mBasePresenter;
    protected BaseActivity context = null;

    public T getmBasePresenter() {
        return mBasePresenter;
    }

    @Override
    public void setPresenter(T presenter) {
        mBasePresenter = presenter;
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

    @Override
    public void onDestroy() {
        if (mBasePresenter != null) {
            mBasePresenter.destroy();
        }
        super.onDestroy();
    }
}
