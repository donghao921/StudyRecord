# Android OkHttp Cookie持久化问题总结
## 方法一
## Android PersistentCookieJar基本使用
1.添加依赖包
```
api 'com.github.franmontiel:PersistentCookieJar:v1.0.1'
```
2.结合Rxjava和Retrofit一起使用简单使用
```
PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
OkHttpBuilder okHttpBuilder = new OkHttpClient.Builder();
okHttpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
okHttpBuilder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
okHttpBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
okHttpBuilder.cookieJar(cookieJar);
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

```
