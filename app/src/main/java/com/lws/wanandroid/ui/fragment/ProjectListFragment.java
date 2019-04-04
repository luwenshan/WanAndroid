package com.lws.wanandroid.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseLoadingFragment;
import com.lws.wanandroid.contract.ProjectListContract;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.model.bean.ProjectListData;
import com.lws.wanandroid.presenter.ProjectListPresenter;
import com.lws.wanandroid.ui.adapter.ProjectListAdapter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.JudgeUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectListFragment extends BaseLoadingFragment<ProjectListPresenter> implements ProjectListContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.project_list_recycler_view)
    RecyclerView mRecyclerView;

    private ProjectListAdapter mAdapter;
    private boolean isRefresh = true;
    private int mCurrentPage;
    private int cid;

    public static ProjectListFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, id);
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initView() {
        super.initView();
        List<ArticleBean> mDatas = new ArrayList<>();
        mAdapter = new ProjectListAdapter(mDatas);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startProjectPager(position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Bundle bundle = getArguments();
        cid = bundle.getInt(Constants.ARG_PARAM1);
        mPresenter.getProjectListData(mCurrentPage, cid, true);
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
        mCurrentPage = 1;
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 1;
            isRefresh = true;
            mPresenter.getProjectListData(mCurrentPage, cid, false);
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            isRefresh = false;
            mPresenter.getProjectListData(mCurrentPage, cid, false);
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void showProjectListData(ProjectListData projectListData) {
        if (isRefresh) {
            mAdapter.replaceData(projectListData.getDatas());
        } else {
            if (projectListData.getDatas().size() > 0) {
                mAdapter.addData(projectListData.getDatas());
            } else {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showCollectOutsideArticle(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showJumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.item_project_list_install_tv:
                startInstallPager(position);
                break;
            default:
                break;
        }
    }

    private void startInstallPager(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        if (TextUtils.isEmpty(mAdapter.getData().get(position).getApkLink())) {
            return;
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mAdapter.getData().get(position).getApkLink())));
    }

    private void startProjectPager(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        JudgeUtil.startArticleDetailActivity(_mActivity,
                null,
                mAdapter.getData().get(position).getId(),
                mAdapter.getData().get(position).getTitle().trim(),
                mAdapter.getData().get(position).getLink().trim(),
                mAdapter.getData().get(position).isCollect(),
                false,
                true);
    }
}
