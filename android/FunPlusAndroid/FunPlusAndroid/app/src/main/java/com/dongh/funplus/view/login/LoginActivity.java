package com.dongh.funplus.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dongh.baselib.mvp.BaseActivity;
import com.dongh.funplus.R;
import com.dongh.funplus.service.bean.LoginBean;
import com.dongh.funplus.view.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    private static final String TAG = "LoginActivity";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.remember_pwd)
    CheckBox remember;
    @BindView(R.id.bt_login)
    Button loginButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ButterKnife.bind(this);
        setPresenter(new LoginPresenter(this));

        boolean rememberPwd = preferences.getBoolean("remember_pwd", false);
        if (rememberPwd) {
            String name = preferences.getString("name", "");
            String password = preferences.getString("password", "");
            loginName.setText(name);
            loginPwd.setText(password);
            remember.setChecked(rememberPwd);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = loginName.getText().toString().trim();
                String password = loginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    editor = preferences.edit();
                    if (remember.isChecked()) {
                        editor.putString("name", name);
                        editor.putString("password", password);
                        editor.putBoolean("remember_pwd", true);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    getmBasePresenter().login(name, password);

                } else {
                    Toast.makeText(getBaseContext(), "empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!remember.isChecked()) {
                    loginName.setText("");
                    loginPwd.setText("");
                    editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                }
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void resultLogin(LoginBean result) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        Toast.makeText(getBaseContext(), "login", Toast.LENGTH_SHORT).show();
    }
}
