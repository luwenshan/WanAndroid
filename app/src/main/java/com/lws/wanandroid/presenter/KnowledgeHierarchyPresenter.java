package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.KnowledgeHierarchyContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class KnowledgeHierarchyPresenter extends BasePresenter<KnowledgeHierarchyContract.View>
        implements KnowledgeHierarchyContract.Presenter {
    @Inject
    public KnowledgeHierarchyPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
