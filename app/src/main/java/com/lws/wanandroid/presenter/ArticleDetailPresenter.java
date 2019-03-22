package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.ArticleDetailContract;
import com.lws.wanandroid.core.DataManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean getAutoCacheState() {
        return false;
    }

    @Override
    public boolean getNoImageState() {
        return false;
    }

    @Override
    public void addCollectArticle(int id) {

    }

    @Override
    public void cancelCollectArticle(int id) {

    }

    @Override
    public void cancelCollectPageArticle(int id) {

    }

    @Override
    public void shareEventPermissionVerify(RxPermissions rxPermissions) {

    }
}
