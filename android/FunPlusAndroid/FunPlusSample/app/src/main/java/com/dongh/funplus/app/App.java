package com.dongh.funplus.app;

import android.app.Application;

import com.dongh.funplus.di.component.AppComponent;
import com.dongh.funplus.di.component.DaggerAppComponent;
import com.dongh.funplus.di.module.AppModule;

/**
 * Created by chenxz on 2017/3/30.
 */
public class App extends Application {

    private static App instance;
    public static AppComponent appComponent = null;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }
}
