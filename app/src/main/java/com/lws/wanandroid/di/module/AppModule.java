package com.lws.wanandroid.di.module;

import com.lws.wanandroid.app.App;
import com.lws.wanandroid.model.DataManager;
import com.lws.wanandroid.model.db.DbHelper;
import com.lws.wanandroid.model.db.DbHelperImpl;
import com.lws.wanandroid.model.http.HttpHelper;
import com.lws.wanandroid.model.http.HttpHelperImpl;
import com.lws.wanandroid.model.prefs.PreferenceHelper;
import com.lws.wanandroid.model.prefs.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
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
