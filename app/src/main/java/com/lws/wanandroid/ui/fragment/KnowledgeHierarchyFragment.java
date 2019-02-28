package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.KnowledgeHierarchyContract;
import com.lws.wanandroid.presenter.KnowledgeHierarchyPresenter;

public class KnowledgeHierarchyFragment extends BaseRootFragment<KnowledgeHierarchyPresenter> implements KnowledgeHierarchyContract.View {

    public static KnowledgeHierarchyFragment newInstance() {
        Bundle args = new Bundle();
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
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
