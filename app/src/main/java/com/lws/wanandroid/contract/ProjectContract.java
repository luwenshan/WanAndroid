package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface ProjectContract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {

    }
}
