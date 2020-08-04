package com.dongh.baselibs.mvp;

import android.content.Context;

/**
* BaseMvpView
*@author dongh
*@time 2020/3/17
**/
public interface BaseMvpView {
    Context getContext();

    void showLoadingDialog();

    void dismissLoadingDialog();

}
