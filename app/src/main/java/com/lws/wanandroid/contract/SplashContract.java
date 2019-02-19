package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface SplashContract {
    interface View extends IView {
        void jumpToMain();
    }

    interface Presenter extends IPresenter<View> {

    }
}
