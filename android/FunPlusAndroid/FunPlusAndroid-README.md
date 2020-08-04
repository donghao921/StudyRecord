## README
## 1. MVP 模式

**该部分内容参考大佬讲解[MVP](http://www.jcodecraeer.com/a/anzhuokaifa/2017/1020/8625.html?1508484926)**

* MVP 模式将Activity 中的业务逻辑全部分离出来，让Activity 只做 UI 逻辑的处理，所有跟Android API无关的业务逻辑由 Presenter 层来完成。
* 将业务处理分离出来后最明显的好处就是管理方便，但是缺点就是增加了代码量。
* 降低耦合，方便维护
    > * Activity 和Fragment 视为View层，负责处理 UI。
    > * Presenter 为业务处理层，既能调用UI逻辑，又能请求数据，该层为纯Java类，不涉及任何Android API。
    > * Model 层中包含着具体的数据请求，数据源。
    > * 三层之间调用顺序为view->presenter->model，为了调用安全着想不可反向调用！不可跨级调用！

**进阶版[MVP](http://www.jcodecraeer.com/a/anzhuokaifa/2017/1024/8636.html)**

> 1. BaseView 设置BaseView基本接口，提供设置P类，对话框显示隐藏等；
```
public interface BaseView<T> {
    /**
     * 设置 presenter
     * @param presenter
     */
    void setPresenter(T presenter);
    /**
     * 显示加载中
     */
    void showLoading();
    /**
     * 隐藏加载
     */
    void hideLoading();
    /**
     * 数据获取失败
     */
    void onError(Throwable throwable);
}
```
> 2. BasePresenter P类接口，创建抽象类 BasePresenterImpl 实现 BasePresenter 的接口，泛型V继承 BaseView，类中将泛型V的对象 mView 绑定或者解绑；覆盖接口里的start 和 destroy 方法，start 方法绑定view和设置P类，destroy方法解绑和解绑view；
    >> 将 Activity 的网络请求等操作放在 Activity 的P类中实现，并通过view的回调，将请求结果在 Activity 中处理；
```
public interface BasePresenter {
    void start();

    void destroy();
}
```
```
public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    public Subscription mSubscription;
    protected Context context;
    protected V mView;

    public BasePresenterImpl(V mView) {
        this.mView = mView;
    }

    @Override
    public void start() {
        attach(mView);
        mView.setPresenter(this);
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        if (isAttach()) {
            detach();
        }
    }

    /**
     * 绑定view，一般在View的OnCreate或onResume中调用
     * @param mView
     */
    public void attach(V mView) {
        this.mView = mView;
    }

    /**
     * 解绑 view ，一般在 View 的 onDestory 或 onStop 中调用
     */
    public void detach() {
        mView = null;
    }

    /**
     * 判断View对象是否存在
     * @return
     */
    public boolean isAttach() {
        return mView != null;
    }

}
```
> 3. BaseActivity 类提供获取P类和设置P类的方法
```
public abstract class BaseActivity<T extends BasePresenter> extends FragmentActivity implements BaseView<T>{
    // 该Activity实例，命名为context是因为大部分方法都只需要context，写成context使用更方便
    protected BaseActivity context = null;
    private T mBasePresenter;

    // 该Activity的界面，即contentView
    protected View view = null;

    // 布局解释器
    protected LayoutInflater inflater = null;

    // Fragment管理器
    protected FragmentManager fragmentManager = null;

    // 用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
    protected Intent intent = null;

    private boolean isAlive = false;
    private boolean isRunning = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = BaseActivity.this;
        isAlive =true;
        fragmentManager = getSupportFragmentManager();
        inflater = getLayoutInflater();
    }

    public T getmBasePresenter() {
        return mBasePresenter;
    }

    @Override
    public void setPresenter(T presenter) {
        mBasePresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        if (mBasePresenter != null) {
            mBasePresenter.destroy();
        }
        super.onDestroy();
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
}
```
## 2. RxJava2+Retrofit2+OkHttp3

**参考网上文章01[RxJava2+Retrofit2+OkHttp3](https://www.jianshu.com/p/0ad99e598dba), 02[Android RxJava2+Retrofit2+OkHttp3](https://www.jianshu.com/p/8cd29bde7545), 03[Retrofit2解析](https://www.jianshu.com/p/345304325511)**

* Retrofit：Retrofit是Square公司开发的一款针对Android 网络请求的框架（底层默认是基于OkHttp 实现）。
* OkHttp：也是Square公司的一款开源的网络请求库。
* RxJava ："a library for composing asynchronous and event-based programs using observable sequences for the Java VM"（一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库）。RxJava使异步操作变得非常简单。

***Retrofit 负责 请求的数据 和 请求的结果，使用 接口的方式 呈现，OkHttp 负责请求的过程，RxJava 负责异步，各种线程之间的切换。***

*** 基本使用
1. 引入依赖包，根据项目需求选择需要的包引入
```
//Retrofit +okhttp +RxJava
api 'com.squareup.okhttp3:okhttp:3.8.1'
api 'com.squareup.okhttp3:logging-interceptor:3.8.1'
api 'com.github.franmontiel:PersistentCookieJar:v1.0.1'     // cookie 保存
api 'com.squareup.retrofit2:retrofit:2.3.0' // retrofit
api 'com.squareup.retrofit2:converter-gson:2.3.0'   //
api 'com.squareup.retrofit2:converter-scalars:2.3.0'
api 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
api 'com.google.code.gson:gson:2.8.5'   //Gson库
api 'io.reactivex.rxjava2:rxandroid:2.0.2'
api 'io.reactivex.rxjava2:rxjava:2.x.y'
```
2. 

## 3. Glide 图片加载库
***参考郭霖的文章[Android图片加载框架解析](https://blog.csdn.net/guolin_blog/article/details/53759439), [Glide 4.0以上用法](https://blog.csdn.net/guolin_blog/article/details/78582548)***
1. 引入依赖包
```
    //图片加载库
    implementation 'com.github.bumptech.glide:glide:4.8.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
```
2. 图片在 ImageView 上加载显示，可以实现加载网络上的图片，加载手机本地资源里的图片，加载应用资源里的图片；
```
String url = "www.text.image";
Glide.with(Context context).load(url).into(ImageView view);
```
主要步骤就三步，with()创建实例 load()加载图片资源 into()显示；
3. load() 方法用于指定待加载的图片资源；Glide 支持各种各样的图片资源，包括网络图片，本地图片，应用资源，二进制流，Uri对象等；
``` 
// 加载本地图片
File file = new File(getExternalCacheDir() + "/image.jpg");
Glide.with(this).load(file).into(imageView);

// 加载应用资源
int resource = R.drawable.image;
Glide.with(this).load(resource).into(imageView);

// 加载二进制流
byte[] image = getImageBytes();
Glide.with(this).load(image).into(imageView);

// 加载Uri对象
Uri imageUri = getImageUri();
Glide.with(this).load(imageUri).into(imageView);
```
4. 占位图的用法，占位图是指在加载过程中，先临时显示一张图片，待图片加载完成后再替代成要加载的图片，我们在load()和into()的过程中间，添加一个 placeholder() 方法，将替补的占位图片资源传入；
```
Glide 4.0 以下：
Glide.with(this)
     .load(url)
     .placeholder(R.drawable.loading)
     .into(imageView);

Glide 4.0 以上：
RequestOptions options = new RequestOptions()
        .placeholder(R.drawable.loading);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);
```
5. 异常占位图，指在异常情况下导致图片加载失败，就显示这张异常占位图；方法为添加 error() 方法
```
Glide 4.0 以下:
Glide.with(this)
     .load(url)
     .placeholder(R.drawable.loading)
     .error(R.drawable.error)
     .into(imageView);

Glide 4.0 以上：
RequestOptions options = new RequestOptions()
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .diskCacheStrategy(DiskCacheStrategy.NONE);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);
```


6. 指定图片大小  一般情况下，Glide会自动根据ImageView的大小来决定图片的大小，如果需要指定图片大小，Glide 提供了 override() 方法指定一个图片的尺寸；
```
RequestOptions options = new RequestOptions()
        .override(200, 100);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);

// 加载原始尺寸大小的图片
RequestOptions options = new RequestOptions()
        .override(Target.SIZE_ORIGINAL);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);
```
7. Glide 获取 Bitmap

Glide 4.0以下版本
```
Bitmap myBitmap = Glide.with(applicationContext)
        .load(yourUrl)
        .asBitmap()
        .centerCrop()
        .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
        .get();
imageView.setImageBitmap(myBitmap);
```
Glide 4.0以上版本
```
方法一：
FutureTarget<Bitmap> bitmap = Glide.with(this)
	.asBitmap()
	.load("yourUrl")
	.submit();
try{
	Bitmap ImageBitmap = bitmap.get();
}catch (Exception e){
	e.printStackTrace();
}

方法二：
SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
        imageView.setImageBitmap(resource);
    }
};

public void loadImage(View view) {
    String url = "http://cn.bing.com/text.jpg";
    Glide.with(this)
         .load(url)
         .into(simpleTarget);
}
```

8. Glide的缓存机制

禁用内存缓存机制，使用 skipMemoryCache() 方法，传入 true
```
RequestOptions options = new RequestOptions()
        .skipMemoryCache(true);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);
```
硬盘缓存禁用，使用 diskCacheStrategy() 方法，接收以下5种参数：
* DiskCacheStrategy.NONE： 表示不缓存任何内容。
* DiskCacheStrategy.DATA： 表示只缓存原始图片。
* DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
* DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
* DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）
```
RequestOptions options = new RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);
```
9. submit()方法

submit()方法只会下载图片，不会对图片进行加载；submit()方法有两个方法重载：
* submit()  用于下载原始尺寸的图片，**submit()方法必须要用在子线程当中**
* submit(int width, int height) 可以指定下载图片的尺寸

> 当调用了submit()方法后会立即返回一个FutureTarget对象，然后Glide会在后台开始下载图片文件。接下来我们调用FutureTarget的get()方法就可以去获取下载好的图片文件了，如果此时图片还没有下载完，那么get()方法就会阻塞住，一直等到图片下载完成才会有值返回。
```
public void downloadImage() {
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                String url = "http://www.guolin.tech/book.png";
                final Context context = getApplicationContext();
                FutureTarget<File> target = Glide.with(context)
                        .asFile()
                        .load(url)
                        .submit();
                final File imageFile = target.get();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
}
```

## 4. HTML转移字符与JAVA互转
1. HTML的特殊转义字符列表

    显示	说明	实体名称	实体编号
 	半方大的空白	&ensp;	 
 	全方大的空白	&emsp;	 
 	不断行的空白格	&nbsp;	 
<	小于	&lt;	<
>	大于	&gt;	>
&	&符号	&amp;	&
"	双引号	&quot;	"
©	版权	&copy;	©
®	已注册商标	&reg;	®
™	商标（美国）	™	™
×	乘号	&times;	×
÷	除号	&divide;	÷

2. 转换方法
    org.apache.commons.lang3包有个StringEscapeUtils
    ```
    implementation 'org.apache.commons:commons-lang3:3.7'

    StringEscapeUtils.unescapeHtml4（str）
    ```

## 5. DrawerLayout 实现侧边滑动抽屉式界面

## 6. Toolbar的详细介绍和自定义Toolbar
参考文章[Toolbar的详细介绍和自定义Toolbar](https://blog.csdn.net/da_caoyuan/article/details/79557704)


