package com.dongh.funplus.http.service;


import com.dongh.funplus.bean.LoginBean;
import com.dongh.funplus.bean.WeatherInfo;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by chenxz on 2017/11/30.
 */
public interface RetrofitService {

    @GET("adat/sk/{cityId}.html")
    Flowable<WeatherInfo> getWeatherInfo(@Path("cityId") String cityId);

    @GET("adat/sk/{cityId}.html")
    Flowable<WeatherInfo> getWeatherInfoWitchCache(@Path("cityId") String cityId);

    @FormUrlEncoded
    @POST("user/register")
    Flowable<LoginBean> login(@Field("username") String username,
                              @Field("password") String password);

}
