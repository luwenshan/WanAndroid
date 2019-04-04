package com.lws.wanandroid.model.prefs;

public interface PreferenceHelper {
    void setLoginAccount(String account);

    void setLoginPassword(String password);

    String getLoginAccount();

    String getLoginPassword();

    void setLoginStatus(boolean isLogin);

    boolean getLoginStatus();

    void setCookie(String domain, String cookie);

    String getCookie(String domain);

    void setCurrentPage(int position);

    int getCurrentPage();

    void setProjectCurrentPage(int position);

    int getProjectCurrentPage();

    boolean getAutoCacheState();

    void setAutoCacheState(boolean b);

    boolean getNoImageState();

    void setNoImageState(boolean b);

    boolean getNightModeState();

    void setNightModeState(boolean b);
}
