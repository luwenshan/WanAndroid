package com.lws.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lws.wanandroid.app.App;

public class ImageLoader {
    /**
     * 使用Glide加载圆形ImageView(如头像)时，不要使用占位图
     */
    public static void load(Context context, String url, ImageView iv) {
        if (!App.getAppComponent().getDataManager().getNoImageState()) {
            GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.DATA).into(iv);
        }
    }
}
