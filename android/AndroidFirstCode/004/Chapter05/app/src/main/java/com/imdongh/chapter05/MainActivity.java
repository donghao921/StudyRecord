package com.imdongh.chapter05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyReceiver broadCastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReveiver localReveiver;

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadCastReceiver = new MyReceiver();
        registerReceiver(broadCastReceiver, intentFilter);

        localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.imdongh.LOCAL_BROADCAST");
        localReveiver = new LocalReveiver();
        localBroadcastManager.registerReceiver(localReveiver, intentFilter1);



        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.imdongh.broadcast.MY_BROADCAST");
                sendBroadcast(intent);
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.imdongh.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }


    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "this is my broadcast.", Toast.LENGTH_SHORT).show();
        }
    }

    class LocalReveiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "this is local broadcast.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastReceiver);
        localBroadcastManager.unregisterReceiver(localReveiver);
    }
}
