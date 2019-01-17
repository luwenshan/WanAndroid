package com.lws.wanandroid.di.module;

import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.db.DbHelper;
import com.lws.wanandroid.core.db.DbHelperImpl;
import com.lws.wanandroid.core.http.HttpHelper;
import com.lws.wanandroid.core.http.HttpHelperImpl;
import com.lws.wanandroid.core.prefs.PreferenceHelper;
import com.lws.wanandroid.core.prefs.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final WanAndroidApp mApp;

    public AppModule(WanAndroidApp app) {
        mApp = app;
    }

    @Provides
    @Singleton
    WanAndroidApp provideApplicationContext() {
        return mApp;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(HttpHelperImpl httpHelperImpl) {
        return httpHelperImpl;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(DbHelperImpl dbHelperImpl) {
        return dbHelperImpl;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(PreferenceHelperImpl preferenceHelperImpl) {
        return preferenceHelperImpl;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        return new DataManager(httpHelper, dbHelper, preferenceHelper);
    }
}
