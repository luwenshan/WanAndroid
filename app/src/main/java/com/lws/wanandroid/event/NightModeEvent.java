package com.lws.wanandroid.event;

public class NightModeEvent {
    private boolean isNightMode;

    public NightModeEvent(boolean isNightMode) {
        this.isNightMode = isNightMode;
    }

    public boolean isNightMode() {
        return isNightMode;
    }

    public void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }
}
