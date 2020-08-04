package com.dongh.funplus.view.main;

import com.dongh.funplus.base.mvp.IModel;
import com.dongh.funplus.base.mvp.IPresenter;
import com.dongh.funplus.base.mvp.IView;
import com.dongh.funplus.bean.LoginBean;
import com.dongh.funplus.bean.WeatherInfo;
import com.dongh.funplus.event.MessageEvent;

import io.reactivex.Flowable;

/**
 * Created by chenxz on 2017/12/2.
 */

public interface MainContract {

    interface View extends IView {
        void updateWeather(WeatherInfo weatherInfo);

        void updateLogin(LoginBean loginBean);

        void setMessage(MessageEvent messageEvent);
    }

    interface Presenter extends IPresenter {
        void loadWeatherData(String cityId);

        void login(String username, String password);
    }

    interface Model extends IModel {
        Flowable<WeatherInfo> loadWeatherData(String cityId);

        Flowable<WeatherInfo> loadWeatherData(String cityId, boolean isUpdate);

        Flowable<LoginBean> login(String username, String password);
    }

}
