package com.lws.wanandroid.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.WxDetailContract;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lws.wanandroid.event.CollectEvent;
import com.lws.wanandroid.event.SwitchNavigationEvent;
import com.lws.wanandroid.event.SwitchProjectEvent;
import com.lws.wanandroid.presenter.WxDetailArticlePresenter;
import com.lws.wanandroid.ui.activity.LoginActivity;
import com.lws.wanandroid.ui.adapter.ArticleListAdapter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.JudgeUtil;
import com.lws.wanandroid.utils.RxBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class WxDetailArticleFragment extends BaseRootFragment<WxDetailArticlePresenter> implements WxDetailContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.we_detail_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_back_ib)
    ImageButton mBackIb;
    @BindView(R.id.search_tint_tv)
    TextView mTintTv;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;

    private int id;
    private int mCurrentPage = 1;
    private ArticleListAdapter mAdapter;
    private boolean isRefresh = true;
    private int articlePosition;
    private String mAuthor;
    private String tint;
    private boolean isSearchStatus;
    private String searchString;

    public static WxDetailArticleFragment newInstance(int id, String name) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, id);
        args.putString(Constants.ARG_PARAM2, name);
        WxDetailArticleFragment fragment = new WxDetailArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_detail_article;
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startArticleDetailPager(view, position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        isSearchStatus = false;
        setRefresh();
        Bundle bundle = getArguments();
        id = bundle.getInt(Constants.ARG_PARAM1, 0);
        if (id == 0) {
            return;
        }
        mAuthor = bundle.getString(Constants.ARG_PARAM2, "");
        initToolbar();

        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
                    mTintTv.setText(tint);
                } else {
                    mTintTv.setText("");
                }
            }
        });

        mPresenter.addRxBindingSubscribe(RxView.clicks(mSearchTv)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> !TextUtils.isEmpty(mSearchEdit.getText().toString().trim()))
                .subscribe(o -> {
                    isSearchStatus = true;
                    isRefresh = true;
                    mCurrentPage = 1;
                    searchString = mSearchEdit.getText().toString();
                    mPresenter.getWxSearchSumData(id, mCurrentPage, mSearchEdit.getText().toString());
                }));

        //重置当前页数，防止页面切换后当前页数为较大而加载后面的数据或没有数据
        mCurrentPage = 1;
        mPresenter.getWxDetailData(id, mCurrentPage, true);
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void reload() {
        if (mPresenter != null) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showWxSearchView(FeedArticleListData wxSearchData) {
        if (isRefresh) {
            mAdapter.replaceData(wxSearchData.getDatas());
        } else {
            if (wxSearchData.getDatas().size() > 0) {
                mAdapter.addData(wxSearchData.getDatas());
            } else {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showWxDetailView(FeedArticleListData wxSumData) {
        if (isRefresh) {
            mAdapter.replaceData(wxSumData.getDatas());
        } else {
            if (wxSumData.getDatas().size() > 0) {
                mAdapter.addData(wxSumData.getDatas());
            } else {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(false));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
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

    private void setRefresh() {
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 1;
            if (id != 0) {
                isRefresh = true;
                mPresenter.getWxDetailData(id, 0, false);
            }
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            if (id != 0) {
                isRefresh = false;
                if (isSearchStatus) {
                    mPresenter.getWxSearchSumData(id, mCurrentPage, searchString);
                } else {
                    mPresenter.getWxDetailData(id, mCurrentPage, false);
                }
            }
            refreshLayout.finishLoadMore(1000);
        });
    }

    private void initToolbar() {
        mBackIb.setVisibility(View.GONE);
        tint = mAuthor + "带你" + getString(R.string.search_tint);
        mTintTv.setText(tint);
    }

    private void startArticleDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        articlePosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
        JudgeUtil.startArticleDetailActivity(_mActivity,
                options,
                mAdapter.getData().get(position).getId(),
                mAdapter.getData().get(position).getTitle().trim(),
                mAdapter.getData().get(position).getLink().trim(),
                mAdapter.getData().get(position).isCollect(),
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
}
