package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.KnowledgeHierarchyContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class KnowledgeHierarchyPresenter extends BasePresenter<KnowledgeHierarchyContract.View>
        implements KnowledgeHierarchyContract.Presenter {
    @Inject
    public KnowledgeHierarchyPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getKnowledgeHierarchyData(boolean isShowError) {
        addSubscribe(mDataManager.getKnowledgeHierarchyData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<KnowledgeHierarchyData>>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_knowledge_data),
                        isShowError) {
                    @Override
                    public void onNext(List<KnowledgeHierarchyData> knowledgeHierarchyDataList) {
                        mView.showKnowledgeHierarchyData(knowledgeHierarchyDataList);
                    }
                }));
    }
}
