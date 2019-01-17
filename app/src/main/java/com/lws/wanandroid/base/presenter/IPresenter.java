package com.lws.wanandroid.base.presenter;

import com.lws.wanandroid.base.view.IView;

import io.reactivex.disposables.Disposable;

public interface IPresenter<T extends IView> {
    void attachView(T view);

    void detachView();

    void addRxBindingSubscribe(Disposable disposable);

    boolean getNightModeState();

    void setLoginStatus(boolean loginStatus);

    boolean getLoginStatus();

    String getLoginAccount();

    void setLoginAccount(String account);

    void setLoginPassword(String password);

    int getCurrentPage();
}
