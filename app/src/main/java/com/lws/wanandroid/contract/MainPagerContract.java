package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.core.bean.main.banner.BannerData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;

import java.util.List;

public interface MainPagerContract {
    interface View extends IView {
        void showAutoLoginSuccess();

        void showAutoLoginFail();

        void showArticleList(FeedArticleListData feedArticleListData, boolean isRefresh);

        void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showBannerData(List<BannerData> bannerDataList);
    }

    interface Presenter extends IPresenter<View> {
        String getLoginPassword();

        void loadMainPagerData();

        void getFeedArticleList(boolean isShowError);

        void loadMoreData();

        void addCollectArticle(int position, FeedArticleData feedArticleData);

        void cancelCollectArticle(int position, FeedArticleData feedArticleData);

        void getBannerData(boolean isShowError);

        void autoRefresh(boolean isShowError);

        void loadMore();
    }
}
