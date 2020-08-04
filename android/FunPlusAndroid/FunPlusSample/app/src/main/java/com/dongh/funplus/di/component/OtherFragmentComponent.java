package com.dongh.funplus.di.component;

import com.dongh.funplus.di.module.OtherFragmentModule;
import com.dongh.funplus.di.scope.FragmentScope;
import com.dongh.funplus.view.other.OtherFragment;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/3.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = OtherFragmentModule.class)
public interface OtherFragmentComponent {
    void inject(OtherFragment fragment);
}
