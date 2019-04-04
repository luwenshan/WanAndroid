package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseLoadingFragment;
import com.lws.wanandroid.base.fragment.BaseMVPFragment;
import com.lws.wanandroid.contract.WxContract;
import com.lws.wanandroid.model.bean.WxAuthor;
import com.lws.wanandroid.event.JumpToTheTopEvent;
import com.lws.wanandroid.presenter.WxArticlePresenter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WxArticleFragment extends BaseLoadingFragment<WxArticlePresenter> implements WxContract.View {

    @BindView(R.id.wx_detail_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.wx_detail_viewpager)
    ViewPager mViewPager;

    private List<BaseMVPFragment> mFragments = new ArrayList<>();

    public static WxArticleFragment newInstance() {
        Bundle args = new Bundle();
        WxArticleFragment fragment = new WxArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_article;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getWxAuthorListData();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void reload() {
        if (mPresenter != null && mTabLayout.getVisibility() == View.INVISIBLE) {
            mPresenter.getWxAuthorListData();
        }
    }

    public void jumpToTheTop() {
        if (mFragments != null) {
            RxBus.getDefault().post(new JumpToTheTopEvent());
        }
    }

    @Override
    public void showWxAuthorListView(List<WxAuthor> wxAuthors) {
        if (mPresenter.getCurrentPage() == Constants.TYPE_WX_ARTICLE) {
            mTabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
        } else {
            mTabLayout.setVisibility(View.INVISIBLE);
            mViewPager.setVisibility(View.INVISIBLE);
        }
        mFragments.clear();
        for (WxAuthor wxAuthor : wxAuthors) {
            mFragments.add(WxDetailArticleFragment.newInstance(wxAuthor.getId(), wxAuthor.getName()));
        }
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 : mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return wxAuthors.get(position).getName();
            }
        });
        mTabLayout.setViewPager(mViewPager);
        showNormal();
    }

    @Override
    public void showError() {
        mTabLayout.setVisibility(View.INVISIBLE);
        mViewPager.setVisibility(View.INVISIBLE);
        super.showError();
    }
}
