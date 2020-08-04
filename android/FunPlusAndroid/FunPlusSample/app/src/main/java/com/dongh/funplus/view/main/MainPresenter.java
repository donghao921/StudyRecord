package com.dongh.funplus.view.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.dongh.funplus.base.BaseSubscriber;
import com.dongh.funplus.base.BasePresenter;
import com.dongh.funplus.bean.LoginBean;
import com.dongh.funplus.bean.WeatherInfo;
import com.dongh.funplus.event.MessageEvent;
import com.dongh.funplus.util.RxUtil;

import org.reactivestreams.Subscription;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenxz on 2017/12/2.
 */

public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View view) {
        super(model, view);
        Log.e(TAG, "MainPresenter: 构造。。。。");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.e(TAG, "onCreate: MainPresenter");
    }

    @Override
    public void loadWeatherData(String cityId) {
//        addDispose(mModel.loadWeatherData(cityId)
//                .subscribe(new Consumer<WeatherInfo>() {
//                    @Override
//                    public void accept(WeatherInfo weatherInfo) throws Exception {
//                        mView.updateWeather(weatherInfo);
//                    }
//                }));

        mModel.loadWeatherData(cityId, false)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                })
                .compose(mView.<WeatherInfo>bindToLife())
                .subscribe(new BaseSubscriber<WeatherInfo>(mView) {
                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        mView.updateWeather(weatherInfo);
                    }
                });

        Flowable.interval(1, TimeUnit.SECONDS)
                .compose(RxUtil.<Long>rxSchedulerTransformer())
                .compose(mView.<Long>bindToLife())
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong % 2 == 0;
                    }
                })
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return aLong + "::ss";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept: " + s);
                    }
                });

    }

    @Override
    public void login(String username, String password) {
        mModel.login(username, password)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                })
                .compose(mView.<LoginBean>bindToLife())
                .subscribe(new BaseSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.updateLogin(loginBean);
                    }
                });


    }

    public void sendMessage() {
        Log.e(TAG, "sendMessage: ");
        EventBus.getDefault().post(new MessageEvent("message"));
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onShowMessage(MessageEvent messageEvent) {
        Log.e(TAG, "onShowMessage: " + messageEvent.getMessage());
        mView.setMessage(messageEvent);
    }
}
