package com.imdongh.chapter02;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainFirstActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1;
    private TextView fitstText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.bt_1);
        button1.setOnClickListener(this);
        fitstText = findViewById(R.id.tv_text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(MainFirstActivity.this, "click add item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(MainFirstActivity.this, "click remove item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.finish_item:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 点击两次返回退出
     * @param keyCode
     * @param event
     * @return
     */

    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                Toast.makeText(MainFirstActivity.this, "finish", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(MainFirstActivity.this, "press back key again", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_1) {
            Toast.makeText(MainFirstActivity.this, "button1", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainFirstActivity.this, SecondACtivity.class);
            intent.putExtra("text", "first");
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 100) {
            fitstText.setText(data.getStringExtra("text"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
