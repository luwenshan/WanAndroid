package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.App;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.ProjectListContract;
import com.lws.wanandroid.model.DataManager;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.model.bean.ProjectListData;
import com.lws.wanandroid.event.JumpToTheTopEvent;
import com.lws.wanandroid.utils.RxBus;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    @Inject
    public ProjectListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(ProjectListContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(JumpToTheTopEvent.class)
                .subscribe(jumpToTheTopEvent -> mView.showJumpToTheTop()));
    }

    @Override
    public void getProjectListData(int page, int cid, boolean isShowError) {
        addSubscribe(mDataManager.getProjectListData(page, cid)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<ProjectListData>(mView,
                        App.getInstance().getString(R.string.failed_to_obtain_project_list),
                        isShowError) {
                    @Override
                    public void onNext(ProjectListData projectListData) {
                        mView.showProjectListData(projectListData);
                    }
                }));
    }

    @Override
    public void addCollectOutsideArticle(int position, ArticleBean feedArticleData) {
        addSubscribe(mDataManager.addCollectOutsideArticle(feedArticleData.getTitle(), feedArticleData.getAuthor(), feedArticleData.getLink())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<ArticleListBean>(mView,
                        App.getInstance().getString(R.string.collect_fail)) {
                    @Override
                    public void onNext(ArticleListBean feedArticleListData) {
                        feedArticleData.setCollect(true);
                        mView.showCollectOutsideArticle(position, feedArticleData, feedArticleListData);
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
