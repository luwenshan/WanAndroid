package com.lws.wanandroid.di.component;

import com.lws.wanandroid.base.fragment.BaseMVPFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseFragmentComponent extends AndroidInjector<BaseMVPFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseMVPFragment> {
    }
}
