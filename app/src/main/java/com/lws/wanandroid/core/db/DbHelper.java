package com.lws.wanandroid.core.db;

import com.lws.wanandroid.core.dao.HistoryData;

import java.util.List;

public interface DbHelper {
    /**
     * 增加历史数据
     */
    List<HistoryData> addHistoryData(String data);

    void clearHistoryData();

    List<HistoryData> loadAllHistoryData();
}
