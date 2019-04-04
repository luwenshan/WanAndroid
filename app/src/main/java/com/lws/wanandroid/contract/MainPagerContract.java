package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.BannerBean;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;

import java.util.List;

public interface MainPagerContract {
    interface View extends IView {
        void showAutoLoginSuccess();

        void showAutoLoginFail();

        void showArticleList(ArticleListBean feedArticleListData, boolean isRefresh);

        void showCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData);

        void showCancelCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData);

        void showBannerData(List<BannerBean> bannerDataList);
    }

    interface Presenter extends IPresenter<View> {
        String getLoginPassword();

        void loadMainPagerData();

        void getFeedArticleList(boolean isShowError);

        void loadMoreData();

        void addCollectArticle(int position, ArticleBean feedArticleData);

        void cancelCollectArticle(int position, ArticleBean feedArticleData);

        void getBannerData(boolean isShowError);

        void autoRefresh(boolean isShowError);

        void loadMore();
    }
}
