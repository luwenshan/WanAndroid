package com.lws.wanandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;

public class CommonUtils {
    public static int dp2px(float dpValue) {
        final float scale = WanAndroidApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void showMessage(Activity activity, String msg) {
        LogHelper.e("showMessage: " + msg);
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackMessage(Activity activity, String msg) {
        LogHelper.e("showSnackMessage: " + msg);
        // 去掉虚拟按键
        activity.getWindow().getDecorView().setSystemUiVisibility(
                // 隐藏虚拟按键栏 | 防止点击屏幕时,隐藏虚拟按键栏又弹了出来
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
        final Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(activity, R.color.white));
        snackbar.setAction("知道了", v -> {
            snackbar.dismiss();
            //隐藏SnackBar时记得恢复隐藏虚拟按键栏,不然屏幕底部会多出一块空白布局出来,很难看
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }).show();
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
    }

    /**
     * 判断2个对象是否相等
     */
    public static boolean isEquals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    /**
     * 检查是否有网络可用
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) WanAndroidApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
