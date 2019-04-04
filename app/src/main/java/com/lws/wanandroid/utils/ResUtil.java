package com.lws.wanandroid.utils;

import android.support.annotation.StringRes;

import com.lws.wanandroid.app.App;

public class ResUtil {
    public static String getString(@StringRes int resId) {
        return App.getInstance().getString(resId);
    }
}
