package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.CollectContract;
import com.lws.wanandroid.presenter.CollectPresenter;

public class CollectFragment extends BaseRootFragment<CollectPresenter> implements CollectContract.View {

    public static CollectFragment newInstance() {
        Bundle args = new Bundle();
        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
