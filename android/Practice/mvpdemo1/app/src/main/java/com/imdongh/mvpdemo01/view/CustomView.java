package com.imdongh.mvpdemo01.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CustomView extends View {
    private int lastX;
    private int lastY;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                Log.i("tag", "start x:"+x+",y:"+y);
                break;

            case MotionEvent.ACTION_MOVE:
                // 计算移动的距离;
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                Log.i("tag", "move x:"+offsetX+",y:"+offsetY);
                // 1.layout()
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);
                // 2.offsetLeftAndRight()和offsetTopAndBottom()
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);
                // 3.LayoutParams（改变布局参数） ??
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft()+offsetX;
//                layoutParams.topMargin = getTop()+offsetY;
//                setLayoutParams(layoutParams);
                // 4.动画
                // 5.scrollTo与scollBy
                ((View)getParent()).scrollBy(-offsetX, -offsetY);

                break;
        }

        return super.onTouchEvent(event);
    }

}
