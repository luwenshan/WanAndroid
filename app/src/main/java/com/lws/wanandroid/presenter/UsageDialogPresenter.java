package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.UsageDialogContract;
import com.lws.wanandroid.model.DataManager;
import com.lws.wanandroid.model.bean.UsefulSiteData;
import com.lws.wanandroid.utils.ResUtil;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class UsageDialogPresenter extends BasePresenter<UsageDialogContract.View> implements UsageDialogContract.Presenter {
    @Inject
    public UsageDialogPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getUsefulSites() {
        addSubscribe(mDataManager.getUsefulSites()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<UsefulSiteData>>(mView,
                        ResUtil.getString(R.string.failed_to_obtain_useful_sites_data)) {
                    @Override
                    public void onNext(List<UsefulSiteData> usefulSiteDataList) {
                        mView.showUsefulSites(usefulSiteDataList);
                    }
                }));
    }
}
