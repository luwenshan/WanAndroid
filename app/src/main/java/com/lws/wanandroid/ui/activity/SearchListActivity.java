package com.lws.wanandroid.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.activity.BaseLoadingActivity;
import com.lws.wanandroid.contract.SearchListContract;
import com.lws.wanandroid.model.bean.ArticleBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.event.SwitchNavigationEvent;
import com.lws.wanandroid.event.SwitchProjectEvent;
import com.lws.wanandroid.presenter.SearchListPresenter;
import com.lws.wanandroid.ui.adapter.ArticleListAdapter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.JudgeUtil;
import com.lws.wanandroid.utils.RxBus;
import com.lws.wanandroid.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchListActivity extends BaseLoadingActivity<SearchListPresenter> implements SearchListContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.search_list_refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.normal_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_list_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private int articlePosition;
    private int mCurrentPage;
    private List<ArticleBean> mArticleList;
    private ArticleListAdapter mAdapter;
    private boolean isAddData;
    private String searchText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void initToolbar() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        searchText = ((String) bundle.get(Constants.SEARCH_TEXT));
        if (!TextUtils.isEmpty(searchText)) {
            mTitleTv.setText(searchText);
        }

        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.search_status_bar_white), 1f);
        if (mPresenter.getNightModeState()) {
            mToolbar.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_gradient_bg));
            setToolbarView(R.color.white, R.drawable.ic_arrow_back_white_24dp);
        } else {
            StatusBarUtil.setStatusDarkColor(getWindow());
            mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            setToolbarView(R.color.title_black, R.drawable.ic_arrow_back_grey_24dp);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getSearchList(mCurrentPage, searchText, true);
        initRecyclerView();
        setRefresh();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void reload() {
        if (mPresenter != null) {
            mPresenter.getSearchList(0, searchText, false);
        }
    }

    @Override
    public void showSearchList(ArticleListBean feedArticleListData) {
        mArticleList = feedArticleListData.getDatas();
        if (isAddData) {
            if (mArticleList.size() > 0) {
                mAdapter.addData(mArticleList);
            } else {
                CommonUtils.showMessage(this, getString(R.string.load_more_no_data));
            }
        } else {
            mAdapter.replaceData(mArticleList);
        }
        showNormal();
    }

    @Override
    public void showCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(this, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, ArticleBean feedArticleData, ArticleListBean feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(this, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showCollectSuccess() {
        showCollectResult(true);
    }

    @Override
    public void showCancelCollectSuccess() {
        showCollectResult(false);
    }

    @OnClick(R.id.search_list_floating_action_btn)
    void onFABClicked(View view) {
        mRecyclerView.smoothScrollToPosition(0);
    }

    private void initRecyclerView() {
        mArticleList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(mArticleList);
        mAdapter.isSearchPage();
        mAdapter.isNightMode(mPresenter.getNightModeState());
        mAdapter.setOnItemClickListener((adapter, view, position) -> startArticleDetailPager(view, position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildrenEvent(view, position));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    private void clickChildrenEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.item_search_pager_chapterName:
                startSingleChapterPager(position);
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

    private void clickTag(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        String superChapterName = mAdapter.getData().get(position).getSuperChapterName();
        if (superChapterName.contains(getString(R.string.open_project))) {
            onBackPressedSupport();
            RxBus.getDefault().post(new SwitchProjectEvent());
        } else if (superChapterName.contains(getString(R.string.navigation))) {
            onBackPressedSupport();
            RxBus.getDefault().post(new SwitchNavigationEvent());
        }
    }

    private void startSingleChapterPager(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        JudgeUtil.startKnowledgeHierarchyDetailActivity(this,
                true,
                mAdapter.getData().get(position).getSuperChapterName(),
                mAdapter.getData().get(position).getChapterName(),
                mAdapter.getData().get(position).getChapterId());
    }

    private void startArticleDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        articlePosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.share_view));
        JudgeUtil.startArticleDetailActivity(this,
                options,
                mAdapter.getData().get(position).getId(),
                mAdapter.getData().get(position).getTitle(),
                mAdapter.getData().get(position).getLink(),
                mAdapter.getData().get(position).isCollect(),
                false,
                false);
    }

    private void showCollectResult(boolean collectResult) {
        if (mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(collectResult);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    private void setToolbarView(@ColorRes int textColor, @DrawableRes int navigationIcon) {
        mTitleTv.setTextColor(ContextCompat.getColor(this, textColor));
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(this, navigationIcon));
    }

    private void likeEvent(int position) {
        if (!mPresenter.getLoginStatus()) {
            startActivity(new Intent(this, LoginActivity.class));
            CommonUtils.showMessage(this, getString(R.string.login_tint));
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

    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            isAddData = false;
            mPresenter.getSearchList(mCurrentPage, searchText, false);
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            isAddData = true;
            mPresenter.getSearchList(mCurrentPage, searchText, false);
            refreshLayout.finishLoadMore(1000);
        });
    }
}
