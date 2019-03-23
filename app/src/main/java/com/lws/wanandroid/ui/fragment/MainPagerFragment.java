package com.lws.wanandroid.ui.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.MainPagerContract;
import com.lws.wanandroid.core.bean.main.banner.BannerData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lws.wanandroid.core.http.cookies.CookiesManager;
import com.lws.wanandroid.event.AutoLoginEvent;
import com.lws.wanandroid.event.LoginEvent;
import com.lws.wanandroid.event.SwitchNavigationEvent;
import com.lws.wanandroid.event.SwitchProjectEvent;
import com.lws.wanandroid.presenter.MainPagerPresenter;
import com.lws.wanandroid.ui.activity.LoginActivity;
import com.lws.wanandroid.ui.adapter.ArticleListAdapter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.GlideImageLoader;
import com.lws.wanandroid.utils.JudgeUtil;
import com.lws.wanandroid.utils.RxBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainPagerFragment extends BaseRootFragment<MainPagerPresenter> implements MainPagerContract.View {


    @BindView(R.id.main_pager_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;

    private List<FeedArticleData> mFeedArticleDataList;
    private ArticleListAdapter mAdapter;

    private int articlePosition;
    private List<String> mBannerTitleList;
    private List<String> mBannerUrlList;
    private Banner mBanner;
    private boolean isRecreate;

    public static MainPagerFragment newInstance(boolean isRecreate) {
        Bundle args = new Bundle();
        args.putBoolean(Constants.ARG_PARAM1, isRecreate);
        MainPagerFragment fragment = new MainPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isRecreate = getArguments().getBoolean(Constants.ARG_PARAM1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_pager;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mFeedArticleDataList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(mFeedArticleDataList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startArticleDetailPager(view, position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.head_banner, null));
        mBanner = mHeaderGroup.findViewById(R.id.head_banner);
        mHeaderGroup.removeView(mBanner);
        mAdapter.addHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void startArticleDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
            return;
        }
        // 记录点击的文章位置，便于在文章内点击收藏返回到此界面时能展示正确的收藏状态
        articlePosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
        FeedArticleData article = mAdapter.getData().get(position);
        JudgeUtil.startArticleDetailActivity(_mActivity,
                options,
                article.getId(),
                article.getTitle(),
                article.getLink(),
                article.isCollect(),
                false,
                false);
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.item_search_pager_chapterName:
                startSingleChapterKnowledgePager(position);
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

    private void startSingleChapterKnowledgePager(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        JudgeUtil.startKnowledgeHierarchyDetailActivity(_mActivity,
                true,
                mAdapter.getData().get(position).getSuperChapterName(),
                mAdapter.getData().get(position).getChapterName(),
                mAdapter.getData().get(position).getChapterId());
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

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        setRefresh();
        if (loggedAndNotRebuilt()) {
            mPresenter.loadMainPagerData();
        } else {
            mPresenter.autoRefresh(true);
        }
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh(false);
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    private boolean loggedAndNotRebuilt() {
        return !TextUtils.isEmpty(mPresenter.getLoginAccount())
                && !TextUtils.isEmpty(mPresenter.getLoginPassword())
                && !isRecreate;
    }

    @Override
    public void showAutoLoginSuccess() {
        if (isAdded()) {
            CommonUtils.showSnackMessage(_mActivity, getString(R.string.auto_login_success));
            RxBus.getDefault().post(new AutoLoginEvent());
        }
    }

    @Override
    public void showAutoLoginFail() {
        mPresenter.setLoginStatus(false);
        CookiesManager.clearAllCookies();
        RxBus.getDefault().post(new LoginEvent(false));
    }

    @Override
    public void showArticleList(FeedArticleListData feedArticleListData, boolean isRefresh) {
        if (mPresenter.getCurrentPage() == Constants.TYPE_MAIN_PAGER) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mFeedArticleDataList = feedArticleListData.getDatas();
            mAdapter.replaceData(feedArticleListData.getDatas());
        } else {
            mFeedArticleDataList.addAll(feedArticleListData.getDatas());
            mAdapter.addData(feedArticleListData.getDatas());
        }
        showNormal();
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showBannerData(List<BannerData> bannerDataList) {
        mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        mBannerUrlList = new ArrayList<>();
        for (BannerData bannerData : bannerDataList) {
            mBannerTitleList.add(bannerData.getTitle());
            bannerImageList.add(bannerData.getImagePath());
            mBannerUrlList.add(bannerData.getUrl());
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(bannerDataList.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(position -> JudgeUtil.startArticleDetailActivity(_mActivity, null,
                0, mBannerTitleList.get(position), mBannerUrlList.get(position),
                false, false, true));
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void showLoginView() {
        mPresenter.getFeedArticleList(false);
    }

    @Override
    public void showLogoutView() {
        mPresenter.getFeedArticleList(false);
    }

    @Override
    public void showCollectSuccess() {
        if (mAdapter != null && mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(true);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showCancelCollectSuccess() {
        if (mAdapter != null && mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(false);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        super.showError();
    }

    @Override
    public void reload() {
        if (mRefreshLayout != null && mPresenter != null
                && mRecyclerView.getVisibility() == View.INVISIBLE
                && CommonUtils.isNetworkConnected()) {
            mRefreshLayout.autoRefresh();
        }
    }

    public Banner getBanner() {
        return mBanner;
    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
