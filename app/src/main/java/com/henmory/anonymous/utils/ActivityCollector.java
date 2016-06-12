package com.henmory.anonymous.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 16/6/12.
 *
 * manage all activities
 */
public class ActivityCollector {
    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        if (activityList != null) {
            activityList.remove(activity);
        }
    }

    /*
    * finish all app acitivies, and both activityList and memory is null
    * */
    public static void finishAllActivity(){
        if (activityList != null) {
            for (Activity activity : activityList) {
                if (activity.isFinishing()) {
                    activityList.remove(activity);
                    activity.finish();
                }
            }
        }
    }
}
