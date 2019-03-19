package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;

public interface WxDetailContract {
    interface View extends IView {
        void showWxSearchView(FeedArticleListData wxSearchData);

        void showWxDetailView(FeedArticleListData wxSumData);

        void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showJumpTheTop();

        void showReloadDetailEvent();
    }

    interface Presenter extends IPresenter<View> {
        void getWxSearchSumData(int id, int page, String k);

        void getWxDetailData(int id, int page, boolean isShowError);

        void addCollectArticle(int position, FeedArticleData feedArticleData);

        void cancelCollectArticle(int position, FeedArticleData feedArticleData);
    }
}
