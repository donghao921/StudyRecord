package com.dongh.sample.mvp.contract;

import com.dongh.baselibs.bean.BaseBean;
import com.dongh.baselibs.mvp.IModel;
import com.dongh.baselibs.mvp.IPresenter;
import com.dongh.baselibs.mvp.IView;
import com.dongh.sample.mvp.model.bean.BannerListBean;
import com.dongh.sample.mvp.model.bean.CollectListBean;
import com.dongh.sample.mvp.model.bean.WeatherInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public interface SampleContract {

    interface View extends IView {

        String getCityId();

        void showWeatherInfo(WeatherInfo weatherInfo);

        void loginSuccess();

        void showBannerList(List<BannerListBean.Banner> bannerList);

        void showCollectList(CollectListBean.Collect collect);

        void logoutSuccess();
    }

    interface Presenter extends IPresenter<View> {

        void getWeatherInfo(String cityId);

        void login(String username, String password);

        void getBannerList();

        void getCollectList(int page);

        void logout();
    }

    interface Model extends IModel {

        Observable<WeatherInfo> getWeatherInfo(String cityId);

        Observable<WeatherInfo> getWeatherInfo(String cityId, boolean isUpdate);

        Observable<BaseBean> login(String username, String password);

        Observable<BannerListBean> getBannerList(boolean isUpdate);

        Observable<CollectListBean> getCollectList(int page);

        Observable<BaseBean> logout();

    }

}
