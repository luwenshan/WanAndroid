package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.UsefulSiteData;

import java.util.List;

public interface UsageDialogContract {
    interface View extends IView {
        void showUsefulSites(List<UsefulSiteData> usefulSiteDataList);
    }

    interface Presenter extends IPresenter<View> {
        void getUsefulSites();
    }
}
