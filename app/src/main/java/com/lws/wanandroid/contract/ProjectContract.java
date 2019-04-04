package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.ProjectClassifyData;

import java.util.List;

public interface ProjectContract {
    interface View extends IView {
        void showProjectClassifyData(List<ProjectClassifyData> projectClassifyDataList);
    }

    interface Presenter extends IPresenter<View> {
        void getProjectClassifyData();

        int getProjectCurrentPage();

        void setProjectCurrentPage(int page);
    }
}
