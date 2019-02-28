package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.ProjectContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {
    @Inject
    public ProjectPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
