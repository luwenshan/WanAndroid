package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.WxContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class WxArticlePresenter extends BasePresenter<WxContract.View> implements WxContract.Presenter {
    @Inject
    public WxArticlePresenter(DataManager dataManager) {
        super(dataManager);
    }
}
