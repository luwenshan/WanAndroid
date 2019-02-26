package com.lws.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lws.wanandroid.R;
import com.lws.wanandroid.base.activity.BaseActivity;
import com.lws.wanandroid.base.fragment.BaseFragment;
import com.lws.wanandroid.contract.MainContract;
import com.lws.wanandroid.presenter.MainPresenter;
import com.lws.wanandroid.ui.fragment.MainPagerFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.fragment_group)
    FrameLayout mFrameGroup;

    private List<BaseFragment> mFragments;
    private TextView mUsTv;
    private MainPagerFragment mMainPagerFragment;
    private int mLastFgIndex;

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showSwitchProject() {

    }

    @Override
    public void showSwitchNavigation() {

    }

    @Override
    public void showAutoLoginView() {

    }

    @Override
    public void showLogoutSuccess() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
