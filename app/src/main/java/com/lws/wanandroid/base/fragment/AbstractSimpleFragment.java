package com.lws.wanandroid.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.utils.CommonUtils;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class AbstractSimpleFragment extends SupportFragment {
    private Unbinder mUnbinder;
    private long clickTime;
    public boolean isInnerFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = WanAndroidApp.getRefWatcher(_mActivity);
        refWatcher.watch(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initEventAndData();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (isInnerFragment) {
                _mActivity.finish();
                return true;
            }
            long currentTime = System.currentTimeMillis();
            if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
                CommonUtils.showSnackMessage(_mActivity, getString(R.string.double_click_exit_tint));
                clickTime = currentTime;
            } else {
                _mActivity.finish();
            }
        }
        return true;
    }

    protected void initView() {
    }

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();
}
