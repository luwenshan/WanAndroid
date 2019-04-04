package com.lws.wanandroid.widget;

import android.text.TextUtils;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.App;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.http.ServerException;
import com.lws.wanandroid.utils.LogHelper;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {
    private IView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    public BaseSubscriber(IView view) {
        mView = view;
    }

    public BaseSubscriber(IView view, String errorMsg) {
        mView = view;
        mErrorMsg = errorMsg;
    }

    public BaseSubscriber(IView view, boolean isShowError) {
        mView = view;
        this.isShowError = isShowError;
    }

    public BaseSubscriber(IView view, String errorMsg, boolean isShowError) {
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
            mView.showErrorMsg(App.getInstance().getString(R.string.http_error));
        } else {
            mView.showErrorMsg(App.getInstance().getString(R.string.unKnown_error));
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
