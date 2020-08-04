package com.dongh.funplus.di.component;

import com.dongh.funplus.di.module.MainActivityModule;
import com.dongh.funplus.di.scope.ActivityScope;
import com.dongh.funplus.view.main.MainActivity;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
