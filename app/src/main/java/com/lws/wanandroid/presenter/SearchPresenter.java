package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.SearchContract;
import com.lws.wanandroid.model.DataManager;
import com.lws.wanandroid.model.bean.TopSearchData;
import com.lws.wanandroid.model.dao.HistoryData;
import com.lws.wanandroid.utils.ResUtil;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
    @Inject
    public SearchPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return mDataManager.loadAllHistoryData();
    }

    @Override
    public void addHistoryData(String data) {
        addSubscribe(Observable.create(
                (ObservableOnSubscribe<List<HistoryData>>) e -> {
                    List<HistoryData> historyData = mDataManager.addHistoryData(data);
                    e.onNext(historyData);
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(historyData -> mView.judgeToTheSearchListActivity()));
    }

    @Override
    public void getTopSearchData() {
        addSubscribe(mDataManager.getTopSearchData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<TopSearchData>>(mView,
                        ResUtil.getString(R.string.failed_to_obtain_top_data)) {
                    @Override
                    public void onNext(List<TopSearchData> topSearchDataList) {
                        mView.showTopSearchData(topSearchDataList);
                    }
                }));
    }

    @Override
    public void clearHistoryData() {
        mDataManager.clearHistoryData();
    }
}
