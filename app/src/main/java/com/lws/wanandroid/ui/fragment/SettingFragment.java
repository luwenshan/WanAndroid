package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.base.fragment.BaseFragment;
import com.lws.wanandroid.contract.SettingContract;
import com.lws.wanandroid.presenter.SettingPresenter;

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View {

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }
}
