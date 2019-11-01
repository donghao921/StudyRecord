package com.imdongh.chapter02;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 活动管理器类
*@author dongh
*@time 2019-11-01
**/
public class ActivityCollector {
    private static ActivityCollector collector;

    public static ActivityCollector getInstance() {
        if (collector == null) {
            collector = new ActivityCollector();
        }
        return collector;
    }

    // 为活动创建集合
    public static List<Activity> activities = new ArrayList<>();
    //
    // 活动入栈
    public static void addActivity (Activity activity) {
        activities.add(activity);
    }

    // 活动出栈
    public static void removeActivity (Activity activity) {
        activities.remove(activity);
    }

    // 所有活动出栈
    public static void finishAll () {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
