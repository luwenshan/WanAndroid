package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.model.bean.ProjectListData;

public interface ProjectListContract {
    interface View extends IView {
        void showProjectListData(ProjectListData projectListData);

        void showCollectOutsideArticle(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData);

        void showCancelCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData);

        void showJumpToTheTop();
    }

    interface Presenter extends IPresenter<View> {
        void getProjectListData(int page, int cid, boolean isShowError);

        void addCollectOutsideArticle(int position, ArticleBean feedArticleData);

        void cancelCollectArticle(int position, ArticleBean feedArticleData);
    }
}
