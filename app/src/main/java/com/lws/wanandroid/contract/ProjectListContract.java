package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lws.wanandroid.core.bean.project.ProjectListData;

public interface ProjectListContract {
    interface View extends IView {
        void showProjectListData(ProjectListData projectListData);

        void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showJumpToTheTop();
    }

    interface Presenter extends IPresenter<View> {
        void getProjectListData(int page, int cid, boolean isShowError);

        void addCollectOutsideArticle(int position, FeedArticleData feedArticleData);

        void cancelCollectArticle(int position, FeedArticleData feedArticleData);
    }
}
