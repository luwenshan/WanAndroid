package com.lws.wanandroid.utils;

import android.support.annotation.StringRes;

import com.lws.wanandroid.app.WanAndroidApp;

public class ResUtil {
    public static String getString(@StringRes int resId) {
        return WanAndroidApp.getInstance().getString(resId);
    }
}
