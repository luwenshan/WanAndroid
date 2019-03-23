package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.KnowledgeHierarchyDetailContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.event.SwitchNavigationEvent;
import com.lws.wanandroid.event.SwitchProjectEvent;
import com.lws.wanandroid.utils.RxBus;

import javax.inject.Inject;

public class KnowledgeHierarchyDetailPresenter extends BasePresenter<KnowledgeHierarchyDetailContract.View>
        implements KnowledgeHierarchyDetailContract.Presenter {
    @Inject
    public KnowledgeHierarchyDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(KnowledgeHierarchyDetailContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(SwitchProjectEvent.class)
                .subscribe(switchProjectEvent -> mView.showSwitchProject()));

        addSubscribe(RxBus.getDefault().toFlowable(SwitchNavigationEvent.class)
                .subscribe(switchNavigationEvent -> mView.showSwitchNavigation()));
    }
}
