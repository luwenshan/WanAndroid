package com.lws.wanandroid.app;

import java.io.File;

public class Constants {
    static final String DB_NAME = "wan_android.db";

    public static final String SHARED_PREFERENCE = "shared_preference";

    /**
     * Path
     */
    public static final String PATH_DATA = WanAndroidApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    /**
     * Avoid double click time area
     */
    public static final long CLICK_TIME_AREA = 1000;

    public static final long DOUBLE_INTERVAL_TIME = 2000;

    /**
     * Shared Preference Keys
     */
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String LOGIN_STATUS = "login_status";
    public static final String AUTO_CACHE_STATE = "auto_cache_state";
    public static final String NO_IMAGE_STATE = "no_image_state";
    public static final String NIGHT_MODE_STATE = "night_mode_state";

    public static final String CURRENT_PAGE = "current_page";
    public static final String PROJECT_CURRENT_PAGE = "project_current_page";

}
