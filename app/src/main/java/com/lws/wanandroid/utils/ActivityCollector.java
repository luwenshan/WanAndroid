package com.lws.wanandroid.utils;

import android.app.Activity;
import android.os.Process;

import java.util.HashSet;
import java.util.Set;

/**
 * 一键退出APP
 */
public class ActivityCollector {
    private static ActivityCollector activityCollector;

    public synchronized static ActivityCollector getInstance() {
        if (activityCollector == null) {
            activityCollector = new ActivityCollector();
        }
        return activityCollector;
    }

    private Set<Activity> allActivities;

    public void addActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (allActivities != null) {
            allActivities.remove(activity);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity activity : allActivities) {
                    activity.finish();
                }
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
