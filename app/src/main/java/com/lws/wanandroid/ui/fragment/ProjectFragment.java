package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseFragment;
import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.ProjectContract;
import com.lws.wanandroid.core.bean.project.ProjectClassifyData;
import com.lws.wanandroid.event.JumpToTheTopEvent;
import com.lws.wanandroid.presenter.ProjectPresenter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseRootFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.project_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.project_divider)
    View mDivider;
    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    private List<ProjectClassifyData> mData;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private int currentPage;

    public static ProjectFragment newInstance() {
        Bundle args = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getProjectClassifyData();
        currentPage = mPresenter.getProjectCurrentPage();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void showProjectClassifyData(List<ProjectClassifyData> projectClassifyDataList) {
        if (mPresenter.getCurrentPage() == Constants.TYPE_PROJECT) {
            setChildViewVisibility(View.VISIBLE);
        } else {
            setChildViewVisibility(View.INVISIBLE);
        }
        mData = projectClassifyDataList;
        initViewPagerAndTabLayout();
        showNormal();
    }

    private void initViewPagerAndTabLayout() {
        for (ProjectClassifyData data : mData) {
            ProjectListFragment projectListFragment = ProjectListFragment.newInstance(data.getId());
            mFragments.add(projectListFragment);
        }
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mData == null ? 0 : mData.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mData.get(position).getName();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void showError() {
        setChildViewVisibility(View.INVISIBLE);
        super.showError();
    }

    @Override
    public void reload() {
        if (mPresenter != null && mTabLayout.getVisibility() == View.INVISIBLE) {
            mPresenter.getProjectClassifyData();
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.setProjectCurrentPage(currentPage);
        }
        super.onDestroyView();
    }

    public void jumpToTheTop() {
        if (mFragments != null) {
            RxBus.getDefault().post(new JumpToTheTopEvent());
        }
    }

    private void setChildViewVisibility(int visibility) {
        mTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
        mViewPager.setVisibility(visibility);
    }
}
