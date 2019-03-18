package com.lws.wanandroid.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.lws.wanandroid.BuildConfig;
import com.lws.wanandroid.R;
import com.lws.wanandroid.core.dao.DaoMaster;
import com.lws.wanandroid.core.dao.DaoSession;
import com.lws.wanandroid.di.component.AppComponent;
import com.lws.wanandroid.di.component.DaggerAppComponent;
import com.lws.wanandroid.di.module.AppModule;
import com.lws.wanandroid.di.module.HttpModule;
import com.lws.wanandroid.utils.logger.TxtFormatStrategy;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class WanAndroidApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    private static WanAndroidApp instance;
    private DaoSession mDaoSession;
    private RefWatcher mRefWatcher;
    public static boolean isFirstRun = true;
    private static AppComponent appComponent;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // 全局设置刷新头部及尾部，优先级最低
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            return new DeliveryHeader(context);
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            return new BallPulseFooter(context).setAnimatingColor(ContextCompat.getColor(context, R.color.colorPrimary));
        });
    }

    public static synchronized WanAndroidApp getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher(Context context) {
        WanAndroidApp app = (WanAndroidApp) context.getApplicationContext();
        return app.mRefWatcher;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build();
        appComponent.inject(this);

        initLogger();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        mRefWatcher = LeakCanary.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    private void initLogger() {
        String appName = getString(R.string.app_name);
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter(
                    PrettyFormatStrategy.newBuilder().tag(appName).build()));
        }
        Logger.addLogAdapter(new DiskLogAdapter(
                TxtFormatStrategy.newBuilder().tag(appName).build(getPackageName(), appName)));
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }
}
