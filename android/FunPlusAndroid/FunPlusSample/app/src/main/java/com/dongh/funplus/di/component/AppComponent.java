package com.dongh.funplus.di.component;


import com.dongh.funplus.app.App;
import com.dongh.funplus.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App getContext();

}
