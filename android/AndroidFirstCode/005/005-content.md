## fragment
* 新建一个自定义 Fragment 类继承自 Fragment，注意，这里会有两个不同的包下的 Fragment 供你选择，一个是系统内置的 android.app.Fragment，一个是 support-V4 库中的 android.support.V4.app.Fragment，推荐直接使用 V4 包下的 Fragment；

## 限定符
> * 大小    small(小屏幕资源) normal(中等屏幕资源) large(大屏幕资源) xlarge(超大屏幕资源)
> * 分辨率  ldpi(低分辨率资源，120dpi以下) mdpi(中等分辨率资源，120~160dpi) hdpi(高分辨率资源，160~240dpi) xhdpi(超高分辨率资源，240~320dpi) xxhdpi(超超高分辨率资源，320~480dpi)
> * 方向    land(横屏设备资源) port(竖屏设备资源)

> 使用限定符关键字为后缀新建资源文件夹，将相应资源文件放入其中