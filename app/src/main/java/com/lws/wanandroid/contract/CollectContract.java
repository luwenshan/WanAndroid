package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface CollectContract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {

    }
}
