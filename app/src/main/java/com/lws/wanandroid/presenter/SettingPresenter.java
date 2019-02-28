package com.lws.wanandroid.presenter;

import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.SettingContract;
import com.lws.wanandroid.core.DataManager;

import javax.inject.Inject;

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {
    @Inject
    public SettingPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
