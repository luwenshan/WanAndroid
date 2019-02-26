package com.lws.wanandroid.ui.fragment;

import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.MainPagerContract;
import com.lws.wanandroid.core.bean.main.banner.BannerData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lws.wanandroid.presenter.MainPagerPresenter;

import java.util.List;

public class MainPagerFragment extends BaseRootFragment<MainPagerPresenter> implements MainPagerContract.View {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void showAutoLoginSuccess() {

    }

    @Override
    public void showAutoLoginFail() {

    }

    @Override
    public void showArticleList(FeedArticleListData feedArticleListData, boolean isRefresh) {

    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {

    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {

    }

    @Override
    public void showBannerData(List<BannerData> bannerDataList) {

    }
}
