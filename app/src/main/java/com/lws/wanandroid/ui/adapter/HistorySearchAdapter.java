package com.lws.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lws.wanandroid.R;
import com.lws.wanandroid.model.dao.HistoryData;
import com.lws.wanandroid.utils.CommonUtils;

import java.util.List;

public class HistorySearchAdapter extends BaseQuickAdapter<HistoryData, BaseViewHolder> {
    public HistorySearchAdapter(@Nullable List<HistoryData> data) {
        super(R.layout.item_search_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryData historyData) {
        helper.setText(R.id.item_search_history_tv, historyData.getData());
        helper.setTextColor(R.id.item_search_history_tv, CommonUtils.randomColor());
        helper.addOnClickListener(R.id.item_search_history_tv);
    }
}
