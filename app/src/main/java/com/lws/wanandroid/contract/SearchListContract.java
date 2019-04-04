package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;

public interface SearchListContract {
    interface View extends IView {
        void showSearchList(ArticleListBean feedArticleListData);

        void showCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData);

        void showCancelCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData);
    }

    interface Presenter extends IPresenter<View> {
        void getSearchList(int page, String k, boolean isShowError);

        void addCollectArticle(int position, ArticleBean feedArticleData);

        void cancelCollectArticle(int position, ArticleBean feedArticleData);
    }
}
