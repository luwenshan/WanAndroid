package com.lws.wanandroid.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.KnowledgeHierarchyContract;
import com.lws.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lws.wanandroid.presenter.KnowledgeHierarchyPresenter;
import com.lws.wanandroid.ui.activity.KnowledgeHierarchyDetailActivity;
import com.lws.wanandroid.ui.adapter.KnowledgeHierarchyAdapter;
import com.lws.wanandroid.utils.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class KnowledgeHierarchyFragment extends BaseRootFragment<KnowledgeHierarchyPresenter> implements KnowledgeHierarchyContract.View {

    @BindView(R.id.rv_knowledge_hierarchy)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;

    private List<KnowledgeHierarchyData> mDataList;
    private KnowledgeHierarchyAdapter mAdapter;
    private boolean isRefresh;

    public static KnowledgeHierarchyFragment newInstance() {
        Bundle args = new Bundle();
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy;
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new KnowledgeHierarchyAdapter(mDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startDetailPager(view, position));
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPresenter.getKnowledgeHierarchyData(false);
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPresenter.getKnowledgeHierarchyData(false);
            refreshLayout.finishLoadMore(1000);
        });
        mPresenter.getKnowledgeHierarchyData(true);
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList) {
        if (mPresenter.getCurrentPage() == 1) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        if (mAdapter.getData().size() < knowledgeHierarchyDataList.size()) {
            mDataList = knowledgeHierarchyDataList;
            mAdapter.replaceData(mDataList);
        } else {
            if (!isRefresh) {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        super.showError();
    }

    @Override
    public void reload() {
        if (mPresenter != null && mRecyclerView.getVisibility() == View.INVISIBLE) {
            mPresenter.getKnowledgeHierarchyData(false);
        }
    }

    private void startDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
        Intent intent = new Intent(_mActivity, KnowledgeHierarchyDetailActivity.class);
        intent.putExtra(Constants.ARG_PARAM1, mAdapter.getData().get(position));
        if (modelFiltering()) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    /**
     * 机型适配
     *
     * @return 返回true表示非三星机型且Android 6.0以上
     */
    private boolean modelFiltering() {
        return !Build.MANUFACTURER.contains(Constants.SAMSUNG) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
