package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;

import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseRootFragment;
import com.lws.wanandroid.contract.WxDetailContract;
import com.lws.wanandroid.presenter.WxDetailArticlePresenter;

public class WxDetailArticleFragment extends BaseRootFragment<WxDetailArticlePresenter> implements WxDetailContract.View {

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
        return 0;
    }
}
