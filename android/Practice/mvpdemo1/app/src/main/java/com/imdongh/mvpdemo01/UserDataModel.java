package com.imdongh.mvpdemo01;

import android.os.Handler;

import com.imdongh.mvpdemo01.base.BaseModel;
import com.imdongh.mvpdemo01.base.CallBack;

public class UserDataModel extends BaseModel<String> {
    @Override
    public void execute(final CallBack<String> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mParams 从父类得到的请求参数
                switch (mParams[0]){
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
