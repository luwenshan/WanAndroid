package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.tbruyelle.rxpermissions2.RxPermissions;

public interface ArticleDetailContract {
    interface View extends IView {
        void showCollectArticleData(FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(FeedArticleListData feedArticleListData);

        void shareEvent();

        void shareError();
    }

    interface Presenter extends IPresenter<View> {
        boolean getAutoCacheState();

        boolean getNoImageState();

        void addCollectArticle(int id);

        void cancelCollectArticle(int id);

        void cancelCollectPageArticle(int id);

        void shareEventPermissionVerify(RxPermissions rxPermissions);
    }
}
