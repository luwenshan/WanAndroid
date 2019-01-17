package com.lws.wanandroid.di.component;

import com.lws.wanandroid.base.activity.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {

    @Subcomponent.Builder
    abstract class BaseBuilder extends Builder<BaseActivity> {

    }

}
