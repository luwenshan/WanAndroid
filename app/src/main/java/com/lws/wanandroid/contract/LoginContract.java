package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface LoginContract {
    interface View extends IView {
        void showLoginSuccess();
    }

    interface Presenter extends IPresenter<View> {
        void getLoginData(String username, String password);
    }
}
