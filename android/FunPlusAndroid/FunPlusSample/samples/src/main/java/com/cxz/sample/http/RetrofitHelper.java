package com.dongh.sample.http;

import com.dongh.baselibs.http.RetrofitManager;
import com.dongh.sample.http.api.Api;
import com.dongh.sample.http.cache.CacheProvider;
import com.dongh.sample.http.cache.WeatherCacheProvider;
import com.dongh.sample.http.service.RetrofitService;
import com.dongh.sample.http.service.WeatherService;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc RetrofitHelper：主要用来创建不同 host 的 RetrofitService 和 CacheService
 */
public class RetrofitHelper {

    /**
     * 获取 RetrofitService
     */
    public static RetrofitService getRetrofitService() {
        return RetrofitManager.getInstance().obtainRetrofitService(Api.WAN_ANDROID_HOST, RetrofitService.class);
    }

    /**
     * 获取 CacheService
     */
    public static CacheProvider getCacheService() {
        return RetrofitManager.getInstance().obtainCacheService(CacheProvider.class);
    }

    public static WeatherService getWeatherService() {
        return RetrofitManager.getInstance().obtainRetrofitService(Api.WEATHER_HOST, WeatherService.class);
    }

    public static WeatherCacheProvider getWeatherCacheService() {
        return RetrofitManager.getInstance().obtainCacheService(WeatherCacheProvider.class);
    }

}
