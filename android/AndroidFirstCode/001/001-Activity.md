## 001 Activity

* Activity 中增加状态栏menu，需要新建menu layout，然后重写 onCreateOptionsMenu 方法，将 menu layout 添加到activity中，然后重写 onOptionsItemSelected 方法，对menu进行点击事件监听；

```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/add_item"
        android:title="Add"/>
    <item
        android:id="@+id/remove_item"
        android:title="Remove"/>

</menu>
```
```
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
            default:
                break;
        }
        return true;
    }
```

* 1. 活动中监听返回键，需要重写 onKeyDown 方法，判断按下为返回键时，finish activity ；
* 2. 实现连续点击两次返回键，结束 activity ,实现方式：
``` 点击两次返回退出
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
```

* 显式 Intent，通过Intent的构造函数，接收一个启动活动的上下文 context, 和指定启动的活动 class，将构造好的 Intent 传入 startActivity 方法，实现显式 Intent 
* 隐式 Intent，并不明确指定要启动的活动，而是指定一系列更抽象的 action 和 category 等信息，交由系统去分析这个 Intent，并帮我们找到合适的 Intent 去启动；
1. > 通过在 AndroidManifest.xml 中的 < activity > 标签下面配置< intent-filter >的内容，可以指定当前活动可以响应的 action 和 category
2. > 每个 Intent 只能指定一个 action, 但是可以指定多个 category
3. > 使用隐式 Intent 不仅可以启动自己程序内的活动，还能启动其他程序的活动，这使多个应用程序之间功能共享成为可能

* 通过 Intent 向下一个活动传递数据，Intent 提供了一系列的 putExtra 方法重载，把我们想要传递的数据暂存在 Intent 中，启动下一个活动后，再通过 getInten.getExtra 方法取出；

## 活动周期
    每个活动在其生命周期中最多有四种状态
    1.运行状态
    2.暂停状态
    3.停止状态
    4.销毁状态

> Activity 类中定义了7个回调方法，覆盖活动生命周期的每一个环节
> * **onCreate** 活动第一次被创建时调用，应该在这个方法中完成活动的初始化操作，如加载布局和绑定事件等
>
> * **onStart** 这个方法在活动由不可见到可见时调用
> * **onResume** 这个方法在活动准备好喝用户交互时调用，此时该活动一定位于栈顶，并处于运行状态
>
> * **onPause** 这个方法在系统准备去启动或者恢复另一个活动时调用，通常会在这个方法中将一些消耗CPU的资源释放掉，以及保存一些关键数据，**但这个方法执行速度一样要快**
>
> * **onStop** 这个方法在活动完全不可见时调用
>
> * **onDestory** 这个方法在活动被销毁前调用，此后活动处于销毁状态
>
> * **onRestart**  这个方法在活动由停止状态变为运行状态之前调用，也就是活动被重新启动

> 以上7个方法中除了 onRestart 都是两两相对
> * *完整生存期* 活动在 onCreate 和 onDestory 方法间所经历的就是完整生存期
> * *可见生存期* 活动在 onStart 和 onStop 方法间所经历的就是可见生存期
> * *前台生存期* 活动在 onResume 和 onPause 方法间所经历的就是前台生存期，在此期间，活动都是处于运行状态

## 活动启动模式
    活动的启动模式一共四种：standard(), singleTop(), singleTask(), singleInstance()
    可以在AndroidManifest.xml中通过给< activity >标签指定 android:launcheMode 属性来选择启动模式

### *standard 模式*
standard是活动的默认启动模式，每当启动一个新活动时，它会在返回栈中入栈，并处于栈顶位置；系统不会在乎这个活动是否已经在返回栈中存在，**每次启动都会创建该活动一个新的实例**
### *singleTop 模式*
singleTop
在启动活动时，发现该活动已位于返回栈的栈顶，则认为可以直接使用它，不需要再创建新的活动实例，(注意，**如果该活动只是处于返回栈中，但并未位于栈顶，仍然会再创建新的活动实例**)
### *singleTask 模式*
很好的解决了 singleTop 模式的问题，该模式下，每次启动活动时，系统会先在返回栈中检查是否存在该活动的实例，如果存在，不论是否在返回栈的栈顶，都认为可以直接使用该活动，不再新建活动实例，**并将该活动之上的所有活动都出栈**，如果没有发现该实例，则新建实例
### *singleInstance 模式*
不同于以上3种模式，singleInstance 模式下活动会启用一个新的返回栈来管理该活动

## 活动管理类
* 需要创建一个 BaseActivity 父类，让项目中的其他 Activity 类都继承这个父类；
* 在父类 BaseActivity 的 onCreate 方法中，使用活动管理器类 ActivityStack 的 push 方法，将所有子类活动入栈；
* 出栈时，根据需要选择 ActivityStack 类中的出栈方法；
``` 活动栈管理器
public class ActivityStack {
    private static ActivityStack stackInstance;
    private static Stack<Activity> activityStack = new Stack<Activity>();

    public static ActivityStack getInstance() {
        if (stackInstance == null) {
            stackInstance = new ActivityStack();
        }
        return stackInstance;
    }

    // 入栈
    public static void push (Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    // 出栈
    public static void pop () {
        try {
            Activity activity = activityStack.lastElement();
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从栈的后面开始出栈，知道自身界面
    public void popTo (Activity activity) {
        if (activity != null) {
            while (true) {
                Activity lastCurrent = top();
                if (lastCurrent == activity) {
                    return;
                }
                activityStack.remove(lastCurrent);
                lastCurrent.finish();
            }
        }
    }

    // 获取栈顶活动
    public Activity top() {
        try {
            if (activityStack.size() >= 1) {
                Activity activity = activityStack.lastElement();
                return activity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 除了栈底活动，其他全部出栈
    public void popAllButButtom () {
        while (true) {
            Activity topActivity = top();
            if (topActivity == null || topActivity == activityStack.firstElement()) {
                return;
            }
            activityStack.remove(topActivity);
            topActivity.finish();
        }
    }

    // 全部出栈
    public void popAll () {
        if (activityStack == null) {
            return;
        }
        while (true) {
            Activity topActivity = top();
            if (topActivity == null) {
                return;
            }
            activityStack.remove(topActivity);
            topActivity.finish();
        }
    }

}
```




