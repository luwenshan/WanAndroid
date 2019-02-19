package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface MainContract {
    interface View extends IView {
        void showSwitchProject();

        void showSwitchNavigation();

        void showAutoLoginView();

        void showLogoutSuccess();
    }

    interface Presenter extends IPresenter<View> {
        void setCurrentPage(int page);

        void setNightModeState(boolean b);

        void logout();
    }
}
