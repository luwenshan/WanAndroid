package com.lws.wanandroid.widget;

import android.text.TextUtils;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.core.http.ServerException;
import com.lws.wanandroid.utils.LogHelper;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T> extends ResourceObserver<T> {
    private IView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    public BaseObserver(IView view) {
        mView = view;
    }

    public BaseObserver(IView view, String errorMsg) {
        mView = view;
        mErrorMsg = errorMsg;
    }

    public BaseObserver(IView view, boolean isShowError) {
        mView = view;
        this.isShowError = isShowError;
    }

    public BaseObserver(IView view, String errorMsg, boolean isShowError) {
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
