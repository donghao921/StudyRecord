package com.dongh.funplus.view.other;


import com.dongh.funplus.di.scope.FragmentScope;
import com.dongh.funplus.base.mvp.BaseModel;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/3.
 */
@FragmentScope
public class OtherModel extends BaseModel implements OtherContract.Model {

    @Inject
    public OtherModel() {
    }

}
