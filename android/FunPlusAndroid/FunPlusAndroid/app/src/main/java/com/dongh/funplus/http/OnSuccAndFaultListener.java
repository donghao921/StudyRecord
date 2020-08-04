package com.dongh.funplus.http;

import com.dongh.funplus.service.bean.BaseData;

/**
 * Created by 眼神 on 2018/3/27.
 */
public interface OnSuccAndFaultListener {
    void onSuccess(BaseData result);

    void onFault(String errorMsg);
}
