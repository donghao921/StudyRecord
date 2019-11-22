### 文件存储
* 将数据存储到文件中
```
public void save() {
    String data = "Data to save";
    FileOutputStream out = null;
    BufferedWriter writer = null;
    try {
        //默认保存在/data/data/<packagename>/files/目录下的
        out = openFileOutput("data", Context.MODE_PRIVATE);
        writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.writer(data);
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
```
> openFileOutput 方法指定文件名，不需要指定文件保存路径，默认保存在/data/data/< packagename >/files/目录下的，默认系统路径有两种模式，MODE_PRIVATE 是默认的操作模式，当指定同名的文件名时，所写入内容将会覆盖原文件内容；MODE_APPEND 模式如果文件已存在，就往文件中追加内容；

* 从文件中读取数据
```
public String load() {
    FileInputStream in = null;
    BufferedReader reader = null;
    StringBuilder content = new StringBuilder();
    try {
        in = openFileInput("data");
        reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return content.toString();
}
```
### SharedPreferences存储
* 有三种方法用于得到 SharedPreferences 对象, SharePreferences 文件都是存放在 /data/data//shared_prefs/ 目录下的。
> * Context 类中的 getSharedPreferences() 方法，可以任意指定其文件名，第一个参数为指定文件名，第二个参数默认为 MODE_PRIVATE，其他模式都被废弃。
> * Activity 类中的 getPreferences() 方法，使用当前活动的类名作为文件名，只接收一个默认模式参数。
> * PreferenceManager类中的 getDefaultSharedPreferences() 方法，使用当前应用程序的包名作为前缀来命名文件名，接收一个 Context 参数；得到 SharedPreferences 对象后，分3步保存参数：
>> 1. 调用 SharedPreferences 对象的 edit() 方法来获取一个 SharedPreferences.Editor 对象；
>> 2. 向 SharedPreferences.Editor 对象中添加数据；
>> 3. 调用 apply() 方法向数据库提交数据；
```
private void setSharePreference(String string) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("key", string);
        editor.apply();
    }
```

* 从 SharedPreferences 中读取数据
```
private void getSharePreference() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String key = sp.getString("key", "");
    }
```
### SQLite数据库存储


