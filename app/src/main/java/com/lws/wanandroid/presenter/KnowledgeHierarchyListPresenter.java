package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.KnowledgeHierarchyListContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lws.wanandroid.event.CollectEvent;
import com.lws.wanandroid.event.KnowledgeJumpTopEvent;
import com.lws.wanandroid.event.ReloadDetailEvent;
import com.lws.wanandroid.utils.ResUtil;
import com.lws.wanandroid.utils.RxBus;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

public class KnowledgeHierarchyListPresenter extends BasePresenter<KnowledgeHierarchyListContract.View>
        implements KnowledgeHierarchyListContract.Presenter {
    @Inject
    public KnowledgeHierarchyListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(KnowledgeHierarchyListContract.View view) {
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

        addSubscribe(RxBus.getDefault().toFlowable(KnowledgeJumpTopEvent.class)
                .subscribe(knowledgeJumpTopEvent -> mView.showJumpTheTop()));

        addSubscribe(RxBus.getDefault().toFlowable(ReloadDetailEvent.class)
                .subscribe(reloadEvent -> mView.showReloadDetailEvent()));
    }

    @Override
    public void getKnowledgeHierarchyDetailData(int page, int cid, boolean isShowError) {
        addSubscribe(mDataManager.getKnowledgeHierarchyDetailData(page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        ResUtil.getString(R.string.failed_to_obtain_knowledge_data),
                        isShowError) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showKnowledgeHierarchyDetailData(feedArticleListData);
                    }
                }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        ResUtil.getString(R.string.collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(true);
                        mView.showCollectArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        ResUtil.getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(false);
                        mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }
}
