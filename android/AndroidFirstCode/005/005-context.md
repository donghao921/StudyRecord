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


