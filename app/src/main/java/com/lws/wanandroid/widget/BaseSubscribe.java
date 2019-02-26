package com.lws.wanandroid.widget;

import android.text.TextUtils;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.core.http.ServerException;
import com.lws.wanandroid.utils.LogHelper;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

public abstract class BaseSubscribe<T> extends ResourceSubscriber<T> {
    private IView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    public BaseSubscribe(IView view) {
        mView = view;
    }

    public BaseSubscribe(IView view, String errorMsg) {
        mView = view;
        mErrorMsg = errorMsg;
    }

    public BaseSubscribe(IView view, boolean isShowError) {
        mView = view;
        this.isShowError = isShowError;
    }

    public BaseSubscribe(IView view, String errorMsg, boolean isShowError) {
        mView = view;
        mErrorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (!TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ServerException) {
            mView.showErrorMsg(e.toString());
        } else if (e instanceof HttpException) {
            mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.http_error));
        } else {
            mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.unKnown_error));
            LogHelper.d(e.toString());
        }
        if (isShowError) {
            mView.showError();
        }
    }

    @Override
    public void onComplete() {

    }
}
