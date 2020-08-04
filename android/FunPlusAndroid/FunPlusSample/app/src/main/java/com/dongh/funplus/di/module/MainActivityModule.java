package com.dongh.funplus.di.module;

import com.dongh.funplus.di.scope.ActivityScope;
import com.dongh.funplus.view.main.MainContract;
import com.dongh.funplus.view.main.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2017/12/2.
 */
@Module
public class MainActivityModule {

    private MainContract.View view;

    public MainActivityModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }

}
