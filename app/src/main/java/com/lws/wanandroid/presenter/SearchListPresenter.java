package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.SearchListContract;
import com.lws.wanandroid.model.DataManager;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.event.CollectEvent;
import com.lws.wanandroid.utils.ResUtil;
import com.lws.wanandroid.utils.RxBus;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

public class SearchListPresenter extends BasePresenter<SearchListContract.View> implements SearchListContract.Presenter {
    @Inject
    public SearchListPresenter(DataManager dataManager) {
        super(dataManager);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .filter(collectEvent -> !collectEvent.isCancelCollectSuccess())
                .subscribe(collectEvent -> mView.showCollectSuccess()));
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .filter(CollectEvent::isCancelCollectSuccess)
                .subscribe(collectEvent -> mView.showCancelCollectSuccess()));
    }

    @Override
    public void getSearchList(int page, String k, boolean isShowError) {
        addSubscribe(mDataManager.getSearchList(page, k)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<ArticleListBean>(mView,
                        ResUtil.getString(R.string.failed_to_obtain_search_data_list),
                        isShowError) {
                    @Override
                    public void onNext(ArticleListBean feedArticleListData) {
                        mView.showSearchList(feedArticleListData);
                    }
                }));
    }

    @Override
    public void addCollectArticle(int position, ArticleBean feedArticleData) {
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<ArticleListBean>(mView,
                        ResUtil.getString(R.string.collect_fail)) {
                    @Override
                    public void onNext(ArticleListBean feedArticleListData) {
                        feedArticleData.setCollect(true);
                        mView.showCollectArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }

    @Override
    public void cancelCollectArticle(int position, ArticleBean feedArticleData) {
        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<ArticleListBean>(mView,
                        ResUtil.getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(ArticleListBean feedArticleListData) {
                        feedArticleData.setCollect(false);
                        mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }
}
