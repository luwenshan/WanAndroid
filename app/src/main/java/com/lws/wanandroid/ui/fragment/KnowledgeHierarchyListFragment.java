package com.lws.wanandroid.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseLoadingFragment;
import com.lws.wanandroid.contract.KnowledgeHierarchyListContract;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.event.CollectEvent;
import com.lws.wanandroid.event.SwitchNavigationEvent;
import com.lws.wanandroid.event.SwitchProjectEvent;
import com.lws.wanandroid.presenter.KnowledgeHierarchyListPresenter;
import com.lws.wanandroid.ui.activity.LoginActivity;
import com.lws.wanandroid.ui.adapter.ArticleListAdapter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.JudgeUtil;
import com.lws.wanandroid.utils.RxBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class KnowledgeHierarchyListFragment extends BaseLoadingFragment<KnowledgeHierarchyListPresenter>
        implements KnowledgeHierarchyListContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_list_recycler_view)
    RecyclerView mRecyclerView;

    private int id;
    private int mCurrentPage;
    private ArticleListAdapter mAdapter;
    private boolean isRefresh = true;
    private int articlePosition;

    public static KnowledgeHierarchyListFragment newInstance(int chapterId) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, chapterId);
        KnowledgeHierarchyListFragment fragment = new KnowledgeHierarchyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new ArticleListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startArticleDetailPager(view, position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        isInnerFragment = true;
        setRefresh();
        Bundle bundle = getArguments();
        id = bundle.getInt(Constants.ARG_PARAM1, 0);
        if (id == 0) {
            return;
        }
        //重置当前页数，防止页面切换后当前页数为较大而加载后面的数据或没有数据
        mCurrentPage = 0;
        mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, id, true);
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void showKnowledgeHierarchyDetailData(ArticleListBean feedArticleListData) {
        if (isRefresh) {
            mAdapter.replaceData(feedArticleListData.getDatas());
        } else {
            if (feedArticleListData.getDatas().size() > 0) {
                mAdapter.addData(feedArticleListData.getDatas());
            } else {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(false));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(true));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showJumpTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showReloadDetailEvent() {
        if (mRefreshLayout != null) {
            mRefreshLayout.autoRefresh();
        }
    }

    private void startArticleDetailPager(View view, int position) {
        articlePosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
        ArticleBean data = mAdapter.getData().get(position);
        JudgeUtil.startArticleDetailActivity(_mActivity, options, data.getId(), data.getTitle().trim(),
                data.getLink().trim(), data.isCollect(), false, false);
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.item_search_pager_chapterName:
                break;
            case R.id.item_search_pager_like_iv:
                likeEvent(position);
                break;
            case R.id.item_search_pager_tag_red_tv:
                clickTag(position);
                break;
            default:
                break;
        }
    }

    private void likeEvent(int position) {
        if (!mPresenter.getLoginStatus()) {
            startActivity(new Intent(_mActivity, LoginActivity.class));
            CommonUtils.showMessage(_mActivity, getString(R.string.login_tint));
            return;
        }
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        if (mAdapter.getData().get(position).isCollect()) {
            mPresenter.cancelCollectArticle(position, mAdapter.getData().get(position));
        } else {
            mPresenter.addCollectArticle(position, mAdapter.getData().get(position));
        }
    }

    private void clickTag(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        String superChapterName = mAdapter.getData().get(position).getSuperChapterName();
        if (superChapterName.contains(getString(R.string.open_project))) {
            RxBus.getDefault().post(new SwitchProjectEvent());
        } else if (superChapterName.contains(getString(R.string.navigation))) {
            RxBus.getDefault().post(new SwitchNavigationEvent());
        }
    }

    private void setRefresh() {
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            if (id != 0) {
                isRefresh = true;
                mPresenter.getKnowledgeHierarchyDetailData(0, id, false);
            }
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            if (id != 0) {
                isRefresh = false;
                mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, id, false);
            }
            refreshLayout.finishLoadMore(1000);
        });
    }
}
