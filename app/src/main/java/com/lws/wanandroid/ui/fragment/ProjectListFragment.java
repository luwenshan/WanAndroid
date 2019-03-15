package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.ProjectListContract;
import com.lws.wanandroid.presenter.ProjectListPresenter;

public class ProjectListFragment extends BaseRootFragment<ProjectListPresenter> implements ProjectListContract.View {
    public static ProjectListFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, id);
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
