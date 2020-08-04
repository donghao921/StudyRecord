package com.dongh.baselibs.rx;

import com.dongh.baselibs.rx.scheduler.IoMainScheduler;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc 统一线程处理类
 */
public class RxSchedulers {

    public static IoMainScheduler ioToMain() {
        return new IoMainScheduler();
    }

}
