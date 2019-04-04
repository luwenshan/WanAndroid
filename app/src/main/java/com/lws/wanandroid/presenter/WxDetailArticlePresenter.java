package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.App;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.WxDetailContract;
import com.lws.wanandroid.model.DataManager;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.event.CollectEvent;
import com.lws.wanandroid.event.JumpToTheTopEvent;
import com.lws.wanandroid.utils.RxBus;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

public class WxDetailArticlePresenter extends BasePresenter<WxDetailContract.View> implements WxDetailContract.Presenter {
    @Inject
    public WxDetailArticlePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(WxDetailContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .filter(collectEvent -> !collectEvent.isCancelCollectSuccess())
                .subscribe(collectEvent -> mView.showCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .filter(CollectEvent::isCancelCollectSuccess)
                .subscribe(collectEvent -> mView.showCancelCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(JumpToTheTopEvent.class)
                .subscribe(knowledgeJumpTopEvent -> mView.showJumpTheTop()));
    }

    @Override
    public void getWxSearchSumData(int id, int page, String k) {
        addSubscribe(mDataManager.getWxSearchSumData(id, page, k)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<ArticleListBean>(mView,
                        App.getInstance().getString(R.string.failed_to_obtain_search_data_list)) {
                    @Override
                    public void onNext(ArticleListBean feedArticleListData) {
                        mView.showWxSearchView(feedArticleListData);
                    }
                }));
    }

    @Override
    public void getWxDetailData(int id, int page, boolean isShowError) {
        addSubscribe(mDataManager.getWxSumData(id, page)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<ArticleListBean>(mView,
                        App.getInstance().getString(R.string.failed_to_obtain_wx_data),
                        isShowError) {
                    @Override
                    public void onNext(ArticleListBean wxSumData) {
                        mView.showWxDetailView(wxSumData);
                    }
                }));
    }

    @Override
    public void addCollectArticle(int position, ArticleBean feedArticleData) {
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<ArticleListBean>(mView,
                        App.getInstance().getString(R.string.collect_fail)) {
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
                        App.getInstance().getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(ArticleListBean feedArticleListData) {
                        feedArticleData.setCollect(false);
                        mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }
}
