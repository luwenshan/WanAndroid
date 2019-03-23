package com.lws.wanandroid.presenter;

import android.Manifest;

import com.lws.wanandroid.R;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.ArticleDetailContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lws.wanandroid.utils.ResUtil;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImageState();
    }

    @Override
    public void addCollectArticle(int id) {
        addSubscribe(mDataManager.addCollectArticle(id)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView, ResUtil.getString(R.string.collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showCollectArticleData(feedArticleListData);
                    }
                }));
    }

    @Override
    public void cancelCollectArticle(int id) {
        addSubscribe(mDataManager.cancelCollectArticle(id)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        ResUtil.getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showCancelCollectArticleData(feedArticleListData);
                    }
                }));
    }

    @Override
    public void cancelCollectPageArticle(int id) {
        addSubscribe(mDataManager.cancelCollectPageArticle(id)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        ResUtil.getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showCancelCollectArticleData(feedArticleListData);
                    }
                }));
    }

    @Override
    public void shareEventPermissionVerify(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        mView.shareEvent();
                    } else {
                        mView.shareError();
                    }
                }));
    }
}
