## Broadcast
在代码中注册广播叫动态注册，在 Androidmanifest.xml 中注册的广播叫静态注册
### 动态注册广播
> 在活动中定义一个内部类 MyReceiver，继承自 BroadcastReceiver，并重写父类的 onReceiver() 方法，简单的在里面加入一个 Toast 提示；

> 然后在 onCreate 方法中，创建一个 IntentFilter 实例，并给他添加一个 "android.net.conn.CONNECTIVITY_CHANGE" 的 action；类似的，我们想要广播接收器监听什么广播，就添加相应的 action；

> 然后新建一个 MyReceiver 实例，通过广播注册方法 registerReceiver() 将 IntentFilter 和 MyReceiver 都传入注册方法；

> 最后，动态注册的广播，一定要取消注册，我们在 onDestroy 方法中调用 unregisterReceiver 方法完成取消注册；
```
public class MainActivity extends AppCompatActivity {
    private MyReceiver broadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadCastReceiver = new MyReceiver();
        registerReceiver(broadCastReceiver, intentFilter);
    }


    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "this is my broadcast.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastReceiver);
    }
}
```

### 静态注册广播，实现开机启动
> 动态注册广播可以自由的控制注册和取消，但是必须启动应用程序后才能生效；静态注册可实现开机即启动广播；

> 先上代码 AndroidManifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.imdongh.chapter05">

    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <receiver
            android:name=".BootCompleteReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED"/>
            </intent-filter>
        </receiver>

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
> 需要创建一个新广播接收器 BootCompleteReceiver，可利用as直接创建，会自动在 manifest 中注册；

> 可以看到在 < application > 标签多了一个< receiver > 标签，所有静态广播都是在这里注册的，name 为我们新建的 BootCompleteReceiver 路径，exported 表示允许这个广播接收器接收本程序以外的广播， enabled 表示启动这个广播接收器；

> 由于 android 系统启动完成时会发出一条 "android.intent.action.BOOT_COMPLETED" 的广播，因此我们需要在 < intent-filter > 标签里加入值为这条广播的 action；

> 相应的，监听系统启动需要一些特定权限，我们需要在 uses-permission 中增加 "android.permission.RECEIVE_BOOT_COMPLETED" 的权限；

> 这样在我们创建的 BootCompleteReceiver 的 onReceive 方法中，便能接收到系统启动广播，并做相应的逻辑处理；

### 发送自定义广播
> 如上相同操作，创建一个新的广播接收器，然后将< receiver > 标签中的 action 自定义一个 name=com.imdongh.broadcast.MY_BROADCAST；

> 在 MainActivity 中定义一个按钮，在按钮的点击事件中，使用 sendBroadcast(intent) 来发送广播；
```
    Intent intent = new Intent("com.imdongh.broadcast.MY_BROADCAST");
    sendBroadcast(intent);
```
### 使用本地广播
之前使用的广播都属于系统全局广播，发出的广播可以被其他任意应用程序接收到；为了安全性，android 定义了一套本地广播机制，使用这个广播发出的广播只能在应用程序内部进行传递，广播接收器也只能接收应用程序发出的广播；
> 本地广播需要用到 LocalBroadcastManager 这个类，然后将动态注册广播和取消注册的方法都替换成 LocalBroadcastManager 这个类下的注册广播和取消广播方法；
```
public class MainActivity extends AppCompatActivity {
    private LocalBroadcastManager localBroadcastManager;
    private LocalReveiver localReveiver;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.imdongh.LOCAL_BROADCAST");
        localReveiver = new LocalReveiver();
        localBroadcastManager.registerReceiver(localReveiver, intentFilter);

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.imdongh.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
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
        localBroadcastManager.unregisterReceiver(localReveiver);
    }
}
```