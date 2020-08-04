package com.dongh.funplus.view.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dongh.baselib.mvp.BasePresenterImpl;
import com.dongh.funplus.http.OnSuccAndFaultListener;
import com.dongh.funplus.http.OnSuccAndFaultSub;
import com.dongh.funplus.model.netsubscribe.LoginSubscribe;
import com.dongh.funplus.service.api.CommonService;
import com.dongh.funplus.service.bean.BaseData;
import com.dongh.funplus.service.bean.LoginBean;

public class LoginPresenter extends BasePresenterImpl<LoginView> {
    private CommonService commonService;

    public LoginPresenter(LoginView mView) {
        super(mView);
    }

    public void login(String username, String password) {
        LoginSubscribe.Login(username, password, new OnSuccAndFaultSub<LoginBean>(new OnSuccAndFaultListener() {
            @Override
            public void onSuccess(BaseData result) {
                LoginBean loginBean = (LoginBean) result.getData();
                Log.i("tag", "String:" + loginBean.toString());
                Log.i("tag", "username:" + loginBean.getUsername());
                Log.i("tag", "id:" + loginBean.getId());
//                Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                mView.resultLogin(loginBean);
            }

            @Override
            public void onFault(String errorMsg) {
//                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));


    }

}
