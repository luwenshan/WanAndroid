package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.WxDetailContract;
import com.lws.wanandroid.core.DataManager;

public class WxDetailArticlePresenter extends BasePresenter<WxDetailContract.View> implements WxDetailContract.Presenter {
    public WxDetailArticlePresenter(DataManager dataManager) {
        super(dataManager);
    }
}
