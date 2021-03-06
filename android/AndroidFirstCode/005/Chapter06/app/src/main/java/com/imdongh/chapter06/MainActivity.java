package com.imdongh.chapter06;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText input;
    private Button save;
    private Button load;
    private Button share1;
    private Button share2;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.et_input);
        save = findViewById(R.id.bt_save);
        load = findViewById(R.id.bt_load);
        share1 = findViewById(R.id.bt_set_share);
        share2 = findViewById(R.id.bt_get_share);
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        share1.setOnClickListener(this);
        share2.setOnClickListener(this);

        text = findViewById(R.id.tv_text);
    }

    private void saveData() {
        String inputString = input.getText().toString().trim();
        Log.i("tag", "input:"+inputString);
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = MainActivity.this.openFileOutput("datadata", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private String loadData() {
        StringBuilder content = new StringBuilder();
        FileInputStream in = null;
        BufferedReader reader = null;
        try {
            in = MainActivity.this.openFileInput("datadata");
            reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("tag", "read:"+content.toString());
        return content.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                saveData();
                break;
            case R.id.bt_load:
                String inputString = loadData();
                if (!TextUtils.isEmpty(inputString)) {
                    text.setText(inputString);
                }
                break;
            case R.id.bt_set_share:
                String inputString2 = loadData();
                setSharePreference(inputString2);
                break;
            case R.id.bt_get_share:
                getSharePreference();
                break;
        }
    }

    private void setSharePreference(String string) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("key", string);
        editor.apply();

    }

    private void getSharePreference() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String key = sp.getString("key", "");
        Toast.makeText(this, "key="+key, Toast.LENGTH_SHORT).show();

    }
}
