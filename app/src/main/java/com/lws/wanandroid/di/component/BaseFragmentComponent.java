package com.lws.wanandroid.di.component;

import com.lws.wanandroid.base.fragment.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseFragmentComponent extends AndroidInjector<BaseFragment> {
    @Subcomponent.Builder
    abstract class BaseBuilder extends Builder<BaseFragment> {

    }
}
