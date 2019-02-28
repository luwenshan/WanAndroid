package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.CollectContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {
    @Inject
    public CollectPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
