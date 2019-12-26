package com.imdongh.mvpdemo01;


import android.os.Handler;

public class MvpModel {
    /**
     * 获取网络接口数据
     */
    public static void getNetData(final String param, final MvpCallback callback) {
        // 利用 方法模拟网络请求数据的耗时操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (param) {
                    case "normal":
                        callback.onSucc("succ");
                        break;
                    case "failure":
                        callback.onFail("failure");
                        break;
                    case "error":
                        callback.onError("err");
                        break;
                }
                callback.onComplete();
            }
        }, 2000);
    }
}
