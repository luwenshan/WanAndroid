package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;

public interface KnowledgeHierarchyDetailContract {
    interface View extends IView {
        void showSwitchProject();

        void showSwitchNavigation();
    }

    interface Presenter extends IPresenter<View> {

    }
}
