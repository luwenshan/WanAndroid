package com.lws.wanandroid.core.db;

import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.core.dao.DaoSession;
import com.lws.wanandroid.core.dao.HistoryData;
import com.lws.wanandroid.core.dao.HistoryDataDao;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

public class DbHelperImpl implements DbHelper {

    private static final int HISTORY_LIST_SIZE = 10;

    private DaoSession mDaoSession;
    private List<HistoryData> mHistoryDataList;
    private String mData;
    private HistoryData mHistoryData;

    @Inject
    DbHelperImpl() {
        mDaoSession = WanAndroidApp.getInstance().getDaoSession();
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        this.mData = data;
        getHistoryDataList();
        createHistoryData();
        if (historyDataForward()) {
            return mHistoryDataList;
        }

        if (mHistoryDataList.size() < HISTORY_LIST_SIZE) {
            getHistoryDataDao().insert(mHistoryData);
        } else {
            mHistoryDataList.remove(0);
            mHistoryDataList.add(mHistoryData);
            getHistoryDataDao().deleteAll();
            getHistoryDataDao().insertInTx(mHistoryDataList);
        }

        return mHistoryDataList;
    }

    @Override
    public void clearHistoryData() {
        mDaoSession.getHistoryDataDao().deleteAll();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return mDaoSession.getHistoryDataDao().loadAll();
    }

    /**
     * 历史数据前移
     *
     * @return 返回true表示查询的数据已存在，只需将其前移到第一项历史记录，否则需要增加新的历史记录
     */
    private boolean historyDataForward() {
        Iterator<HistoryData> iterator = mHistoryDataList.iterator();
        while (iterator.hasNext()) {
            HistoryData historyData = iterator.next();
            if (historyData.getData().equals(mData)) {
                mHistoryDataList.remove(historyData);
                mHistoryDataList.add(historyData);
                getHistoryDataDao().deleteAll();
                getHistoryDataDao().insertInTx(mHistoryDataList);
                return true;
            }
        }
        return false;
    }

    private void getHistoryDataList() {
        mHistoryDataList = getHistoryDataDao().loadAll();
    }

    private void createHistoryData() {
        mHistoryData = new HistoryData();
        mHistoryData.setDate(System.currentTimeMillis());
        mHistoryData.setData(mData);
    }

    private HistoryDataDao getHistoryDataDao() {
        return mDaoSession.getHistoryDataDao();
    }
}
