package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.NavigationContract;
import com.lws.wanandroid.presenter.NavigationPresenter;

public class NavigationFragment extends BaseRootFragment<NavigationPresenter> implements NavigationContract.View {

    public static NavigationFragment newInstance() {
        Bundle args = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    public void jumpToTheTop() {

    }
}
