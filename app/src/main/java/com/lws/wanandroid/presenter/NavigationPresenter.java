package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.NavigationContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {
    @Inject
    public NavigationPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
