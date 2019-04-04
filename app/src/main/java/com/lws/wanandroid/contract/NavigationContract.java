package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.NavigationListData;

import java.util.List;

public interface NavigationContract {
    interface View extends IView {
        void showNavigationListData(List<NavigationListData> navigationDataList);
    }

    interface Presenter extends IPresenter<View> {
        void getNavigationListData(boolean isShowError);
    }
}
