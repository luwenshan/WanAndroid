package com.lws.wanandroid.presenter;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.base.presenter.BasePresenter;
import com.lws.wanandroid.contract.WxContract;
import com.lws.wanandroid.core.DataManager;
import com.lws.wanandroid.core.bean.wx.WxAuthor;
import com.lws.wanandroid.utils.RxUtils;
import com.lws.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class WxArticlePresenter extends BasePresenter<WxContract.View> implements WxContract.Presenter {
    @Inject
    public WxArticlePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getWxAuthorListData() {
        addSubscribe(mDataManager.getWxAuthorListData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<WxAuthor>>(mView,
                        WanAndroidApp.getInstance().getString(R.string.fail_to_obtain_wx_author)) {
                    @Override
                    public void onNext(List<WxAuthor> wxAuthors) {
                        mView.showWxAuthorListView(wxAuthors);
                    }
                }));
    }
}
