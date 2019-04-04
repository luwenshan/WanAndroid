package com.lws.wanandroid.di.component;

import com.lws.wanandroid.app.App;
import com.lws.wanandroid.model.DataManager;
import com.lws.wanandroid.di.module.AbstractAllActivityModule;
import com.lws.wanandroid.di.module.AbstractAllDialogFragmentModule;
import com.lws.wanandroid.di.module.AbstractAllFragmentModule;
import com.lws.wanandroid.di.module.AppModule;
import com.lws.wanandroid.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AbstractAllActivityModule.class,
        AbstractAllFragmentModule.class,
        AbstractAllDialogFragmentModule.class,
        AppModule.class,
        HttpModule.class})
public interface AppComponent {
    void inject(App wanAndroidApp);

    App getContext();

    DataManager getDataManager();
}
