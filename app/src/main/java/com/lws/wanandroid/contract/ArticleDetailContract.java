package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

public interface ArticleDetailContract {
    interface View extends IView {
        void showCollectArticleData(ArticleListBean feedArticleListData);

        void showCancelCollectArticleData(ArticleListBean feedArticleListData);

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
