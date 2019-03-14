package com.lws.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lws.wanandroid.R;
import com.lws.wanandroid.core.bean.navigation.NavigationListData;

import java.util.List;

public class NavigationAdapter extends BaseQuickAdapter<NavigationListData, BaseViewHolder> {
    public NavigationAdapter(@Nullable List<NavigationListData> data) {
        super(R.layout.item_navigation, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NavigationListData navigationListData) {

    }
}
