package com.dongh.funplus.http;

import com.dongh.funplus.app.BaseApplication;
import com.dongh.funplus.app.Constant;
import com.dongh.funplus.service.api.CommonService;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
*RetrofitFactory
*@author dongh
*@time 2020/3/20
**/
public class RetrofitFactory {
    public static String TAG = RetrofitFactory.class.getSimpleName();
    public static String CACHE_NAME = "android";
    public static long MAX_CACHE_SIZE = 1024*1024*50;
    private static final int DEFAULT_CONNECT_TIMEOUT = 30;
    private static final int DEFAULT_WRITE_TIMEOUT = 30;
    private static final int DEFAULT_READ_TIMEOUT = 30;
    private int RETRY_COUNT = 0;    //请求失败重连次数
    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit retrofit;
    private CommonService cs;
    private PersistentCookieJar cookieJar;



    private RetrofitFactory() {
        // 创建一个OkHttpClient并配置
        initOkhttpBuilder();
        createRetrofit();
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cs = retrofit.create(CommonService.class);
    }

    private void initOkhttpBuilder() {
        okHttpBuilder = new OkHttpClient.Builder();
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new
                SharedPrefsCookiePersistor(BaseApplication.mContext));
        // 设置缓存
        setCacheInterceptor();
        // 设置 head 信息
        setHeadInterceptor();
        // 设置 Debug Log 模式
        setDebugLogInterceptor();
        // 设置超时时间
        okHttpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.retryOnConnectionFailure(true);   // 错误重连
        okHttpBuilder.cookieJar(cookieJar);
    }

    private void setDebugLogInterceptor() {
        // todo 添加 debug 判断
        // if (BuildConfig.DEBUG)
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.i(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(httpLoggingInterceptor);
    }

    private void setHeadInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body());
                requestBuilder.addHeader("Authorization", "Bearer " + Constant.TOKEN);//添加请求头信息，服务器进行token有效性验证
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        };
        okHttpBuilder.addInterceptor(headerInterceptor);
    }

    private void setCacheInterceptor() {
        File cacheFile = new File(BaseApplication.mContext.getExternalCacheDir(), CACHE_NAME);
        Cache cache = new Cache(cacheFile, MAX_CACHE_SIZE);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (!NetUtil.isNetworkConnected()) {
                    // 有网络时 设置缓存超时时间0个小时
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age="+maxAge)
                            .removeHeader(CACHE_NAME)   // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(CACHE_NAME)
                            .build();
                }

                return response;
            }
        };
        okHttpBuilder.cache(cache).addInterceptor(cacheInterceptor);
    }

    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public static RetrofitFactory getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * 获取CommonService
     * @return
     */
    public CommonService getCommService() {
        return cs;
    }

    /**
     *
     * @param baseUrl
     */
    public void changeBaseUrl(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cs = retrofit.create(CommonService.class);
    }

    /**
     * 设置订阅 和 所在的线程环境
     */
    public <T> void toSubscribe(Observable<T> observable, DisposableObserver<T> disposableObserver) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)
                .subscribe(disposableObserver);
    }

}
