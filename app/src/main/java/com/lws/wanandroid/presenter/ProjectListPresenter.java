package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.ProjectListContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    @Inject
    public ProjectListPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
