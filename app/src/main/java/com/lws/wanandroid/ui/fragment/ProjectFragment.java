package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.ProjectContract;
import com.lws.wanandroid.presenter.ProjectPresenter;

public class ProjectFragment extends BaseRootFragment<ProjectPresenter> implements ProjectContract.View {

    public static ProjectFragment newInstance() {
        Bundle args = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
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
