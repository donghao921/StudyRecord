package com.dongh.funplus.view.login;

import com.dongh.baselib.mvp.BaseView;
import com.dongh.funplus.service.bean.LoginBean;

public interface LoginView extends BaseView<LoginPresenter> {
    void resultLogin(LoginBean result);

}
