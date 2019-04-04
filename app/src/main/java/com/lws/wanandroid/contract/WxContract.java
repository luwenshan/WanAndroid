package com.lws.wanandroid.contract;

import com.lws.wanandroid.base.presenter.IPresenter;
import com.lws.wanandroid.base.view.IView;
import com.lws.wanandroid.model.bean.WxAuthor;

import java.util.List;

public interface WxContract {
    interface View extends IView {
        void showWxAuthorListView(List<WxAuthor> wxAuthors);
    }

    interface Presenter extends IPresenter<View> {
        void getWxAuthorListData();
    }
}
