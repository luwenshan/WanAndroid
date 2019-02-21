package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.MainContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    @Override
    public void setCurrentPage(int page) {

    }

    @Override
    public void setNightModeState(boolean b) {

    }

    @Override
    public void logout() {

    }

    private void registerEvent() {

    }
}
