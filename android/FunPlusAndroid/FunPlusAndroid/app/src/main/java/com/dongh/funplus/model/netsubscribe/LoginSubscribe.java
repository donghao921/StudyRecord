package com.dongh.funplus.model.netsubscribe;

import android.content.Context;

import com.dongh.funplus.http.RetrofitFactory;
import com.dongh.funplus.service.bean.BaseData;
import com.dongh.funplus.service.bean.LoginBean;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
*LoginSubscribe  功能模块来分别存放不同的请求方法，比如登录注册类LoginSubscribe
*@author dongh
*@time 2020/3/20
**/
public class LoginSubscribe{
    public static void Login(String username, String password, DisposableObserver<BaseData<LoginBean>> subscriber) {
        Observable<BaseData<LoginBean>> observable = RetrofitFactory.getInstance().getCommService().login(username, password);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);

    }

}
