package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.MainPagerContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;

import javax.inject.Inject;

public class MainPagerPresenter extends BasePresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    private DataManager mDataManager;
    private boolean isRefresh = true;
    private int mCurrentPage;

    @Inject
    public MainPagerPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public String getLoginPassword() {
        return null;
    }

    @Override
    public void loadMainPagerData() {

    }

    @Override
    public void getFeedArticleList(boolean isShowError) {

    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {

    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {

    }

    @Override
    public void getBannerData(boolean isShowError) {

    }

    @Override
    public void autoRefresh(boolean isShowError) {

    }

    @Override
    public void loadMore() {

    }
}
