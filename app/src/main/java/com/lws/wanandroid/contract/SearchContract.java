package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.TopSearchData;
import com.lws.wanandroid.model.dao.HistoryData;

import java.util.List;

public interface SearchContract {
    interface View extends IView {
        void showHistoryData(List<HistoryData> historyDataList);

        void showTopSearchData(List<TopSearchData> topSearchDataList);

        void judgeToTheSearchListActivity();
    }

    interface Presenter extends IPresenter<View> {
        List<HistoryData> loadAllHistoryData();

        void addHistoryData(String data);

        void getTopSearchData();

        void clearHistoryData();
    }
}
