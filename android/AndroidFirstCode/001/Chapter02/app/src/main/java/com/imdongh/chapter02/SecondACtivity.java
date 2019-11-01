package com.imdongh.chapter02;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondACtivity extends BaseActivity implements View.OnClickListener {
    private Button button2;
    private TextView secondText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button2 = findViewById(R.id.bt_2);
        button2.setOnClickListener(this);
        secondText = findViewById(R.id.tv_text2);

        String data = getIntent().getStringExtra("text");
        secondText.setText(data);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_2) {
            // todo
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("text", "second");
            setResult(100, intent);
            ActivityCollector.getInstance().finishAll();
//            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
