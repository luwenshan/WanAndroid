package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface SettingContract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
        boolean getAutoCacheState();

        void setAutoCacheState(boolean b);

        boolean getNoImageState();

        void setNoImageState(boolean b);

        void setNightModeState(boolean b);
    }
}
