package com.dongh.funplus.app;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

/**
* BaseApplication
*@author dongh
*@time 2020/3/20
**/
public class BaseApplication extends Application {
    public static Context mContext;
    public static BaseApplication app;
    private static PersistentCookieJar mCookieJar = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        app = this;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static PersistentCookieJar getCookieJar() {
        if (mCookieJar == null) {
            mCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
        }
        return mCookieJar;
    }


}
