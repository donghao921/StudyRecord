package com.dongh.funplus.view.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dongh.funplus.R;
import com.dongh.funplus.app.App;
import com.dongh.funplus.base.BaseActivity;
import com.dongh.funplus.bean.LoginBean;
import com.dongh.funplus.bean.WeatherInfo;
import com.dongh.funplus.di.component.DaggerMainActivityComponent;
import com.dongh.funplus.di.module.MainActivityModule;
import com.dongh.funplus.event.MessageEvent;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initInject() {
        DaggerMainActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.button)
    public void doClick(View view) {
        showLoading();
        mPresenter.sendMessage();
        mPresenter.login("funtest", "123456");

//        mPresenter.
//        mPresenter.loadWeatherData("101010100");
    }

    @Override
    public void updateWeather(WeatherInfo weatherInfo) {
        hideLoading();
        StringBuilder s = new StringBuilder();
        s.append(weatherInfo.getWeatherinfo().getCity()+":")
                .append(weatherInfo.getWeatherinfo().getTemp());
        textView2.setText(weatherInfo.toString());
    }

    @Override
    public void updateLogin(LoginBean loginBean) {
        textView2.setText(loginBean.toString());
    }

    @Override
    public void setMessage(MessageEvent messageEvent) {
        textView.setText(messageEvent.getMessage());
    }
}
