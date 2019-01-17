package com.lws.wanandroid.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.utils.CommonUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * MVP 模式的 BaseFragment
 */
public abstract class BaseFragment<T extends IPresenter> extends AbstractSimpleFragment implements IView {
    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter = null;
        }
    }

    @Override
    public void useNightMode(boolean useNightMode) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (isAdded()) {
            CommonUtils.showSnackMessage(_mActivity, errorMsg);
        }
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showToast(String message) {
        CommonUtils.showMessage(_mActivity, message);
    }

    @Override
    public void showSnackBar(String message) {
        CommonUtils.showSnackMessage(_mActivity, message);
    }
}
