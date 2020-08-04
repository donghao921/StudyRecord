package com.dongh.funplus.view.other;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dongh.funplus.R;
import com.dongh.funplus.app.App;
import com.dongh.funplus.base.BaseFragment;
import com.dongh.funplus.di.component.DaggerOtherFragmentComponent;
import com.dongh.funplus.di.module.OtherFragmentModule;
import com.dongh.funplus.event.MessageEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxz on 2017/12/3.
 */

public class OtherFragment extends BaseFragment<OtherPresenter> implements OtherContract.View {

    @BindView(R.id.textView3)
    TextView textView;

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
        return R.layout.fragment_other;
    }

    @Override
    protected void initInject() {
        DaggerOtherFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .otherFragmentModule(new OtherFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.button2)
    public void doClick(View view) {
        mPresenter.sendMessage();
    }

    @Override
    public void setMessage(MessageEvent messageEvent) {
        textView.setText(messageEvent.getMessage());
    }
}
