package com.dongh.funplus.di.component;


import com.dongh.funplus.di.module.ActivityModule;
import com.dongh.funplus.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

//    Activity getActivity();

//    void inject(MainActivity mainActivity);

}
