package com.imdongh.mvpdemo01;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.imdongh.mvpdemo01.base.BaseActivity;

public class MainActivity extends BaseActivity implements MvpView {
    private TextView text;
    MvpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        //
        progressDialog.setMessage("正在加载数据");
        presenter = new MvpPresenter();
        presenter.attachView(this);

    }

    public void getData(View view) {
        presenter.getData("normal");
    }

    public void getDataForFailure(View view) {
        presenter.getData("failure");
    }

    public void getDataForError(View view) {
        presenter.getData("error");
    }

    @Override
    public void showData(String data) {
        text.setText(data);
    }

    @Override
    public void showFailureMessage(String message) {
        text.setText(message);
    }

    @Override
    public void showErrorMessage() {
        text.setText("网络请求异常");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
