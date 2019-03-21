package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface RegisterContract {
    interface View extends IView {
        void showRegisterSuccess();
    }

    interface Presenter extends IPresenter<View> {
        void getRegisterData(String username, String password, String rePassword);
    }
}
