package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.NavigationContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.bean.navigation.NavigationListData;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {
    @Inject
    public NavigationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getNavigationListData(boolean isShowError) {
        addSubscribe(mDataManager.getNavigationListData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<NavigationListData>>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_navigation_list),
                        isShowError) {
                    @Override
                    public void onNext(List<NavigationListData> navigationListData) {
                        mView.showNavigationListData(navigationListData);
                    }
                }));
    }
}
