package com.lws.wanandroid.base.view;

public interface IView {
    /**
     * 夜间模式
     */
    void useNightMode(boolean useNightMode);

    void showErrorMsg(String errorMsg);

    void showNormal();

    void showError();

    void showLoading();

    void reload();

    void showLoginView();

    void showLogoutView();

    void showCollectSuccess();

    void showCancelCollectSuccess();

    void showToast(String message);

    void showSnackBar(String message);
}
