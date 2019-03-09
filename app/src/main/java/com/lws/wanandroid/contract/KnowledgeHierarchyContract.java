package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;

import java.util.List;

public interface KnowledgeHierarchyContract {
    interface View extends IView {
        void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList);
    }

    interface Presenter extends IPresenter<View> {
        void getKnowledgeHierarchyData(boolean isShowError);
    }
}
