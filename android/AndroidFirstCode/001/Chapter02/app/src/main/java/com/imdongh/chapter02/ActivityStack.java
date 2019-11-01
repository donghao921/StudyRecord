package com.imdongh.chapter02;

import android.app.Activity;

import java.util.Stack;

/**
* 活动栈管理器
*@author dongh
*@time 2019-11-01
**/
public class ActivityStack {
    private static ActivityStack stackInstance;
    private static Stack<Activity> activityStack = new Stack<Activity>();

    public static ActivityStack getInstance() {
        if (stackInstance == null) {
            stackInstance = new ActivityStack();
        }
        return stackInstance;
    }

    // 入栈
    public static void push (Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    // 出栈
    public static void pop () {
        try {
            Activity activity = activityStack.lastElement();
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从栈的后面开始出栈，知道自身界面
    public void popTo (Activity activity) {
        if (activity != null) {
            while (true) {
                Activity lastCurrent = top();
                if (lastCurrent == activity) {
                    return;
                }
                activityStack.remove(lastCurrent);
                lastCurrent.finish();
            }
        }
    }

    // 获取栈顶活动
    public Activity top() {
        try {
            if (activityStack.size() >= 1) {
                Activity activity = activityStack.lastElement();
                return activity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 除了栈底活动，其他全部出栈
    public void popAllButButtom () {
        while (true) {
            Activity topActivity = top();
            if (topActivity == null || topActivity == activityStack.firstElement()) {
                return;
            }
            activityStack.remove(topActivity);
            topActivity.finish();
        }
    }

    // 全部出栈
    public void popAll () {
        if (activityStack == null) {
            return;
        }
        while (true) {
            Activity topActivity = top();
            if (topActivity == null) {
                return;
            }
            activityStack.remove(topActivity);
            topActivity.finish();
        }
    }

}
