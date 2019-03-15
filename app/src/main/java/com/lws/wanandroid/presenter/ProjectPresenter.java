package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.ProjectContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.bean.project.ProjectClassifyData;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {
    @Inject
    public ProjectPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getProjectClassifyData() {
        addSubscribe(mDataManager.getProjectClassifyData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<ProjectClassifyData>>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_project_classify_data)) {
                    @Override
                    public void onNext(List<ProjectClassifyData> projectClassifyData) {
                        mView.showProjectClassifyData(projectClassifyData);
                    }
                }));
    }

    @Override
    public int getProjectCurrentPage() {
        return mDataManager.getProjectCurrentPage();
    }

    @Override
    public void setProjectCurrentPage(int page) {
        mDataManager.setProjectCurrentPage(page);
    }
}
