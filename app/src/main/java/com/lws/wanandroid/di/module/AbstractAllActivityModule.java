package com.lws.wanandroid.di.module;

import com.lws.wanandroid.di.component.BaseActivityComponent;
import com.lws.wanandroid.ui.activity.LoginActivity;
import com.lws.wanandroid.ui.activity.MainActivity;
import com.lws.wanandroid.ui.activity.RegisterActivity;
import com.lws.wanandroid.ui.activity.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity contributesSplashActivityInjector();

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributesLoginActivityInjector();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity contributesRegisterActivityInjector();
}
