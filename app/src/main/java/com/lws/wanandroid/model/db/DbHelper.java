package com.lws.wanandroid.model.db;

import com.lws.wanandroid.model.dao.HistoryData;

import java.util.List;

public interface DbHelper {
    /**
     * 增加历史数据
     */
    List<HistoryData> addHistoryData(String data);

    void clearHistoryData();

    List<HistoryData> loadAllHistoryData();
}
