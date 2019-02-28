package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.WxContract;
import com.lws.wanandroid.presenter.WxArticlePresenter;

public class WxArticleFragment extends BaseRootFragment<WxArticlePresenter> implements WxContract.View {

    public static WxArticleFragment newInstance() {
        Bundle args = new Bundle();
        WxArticleFragment fragment = new WxArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return 0;
    }

    public void jumpToTheTop() {

    }
}
