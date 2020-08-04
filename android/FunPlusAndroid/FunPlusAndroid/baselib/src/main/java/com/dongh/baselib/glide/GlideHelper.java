package com.dongh.baselib.glide;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideHelper {
    /**
     * 加载图片显示
     * @param context
     * @param view
     * @param url
     */
    public static void loadImage(Context context, String url, View view) {
        Glide.with(context).load(url).into((ImageView) view);
    }

    /**
     * 加载图片，以正方形显示
     * @param context
     * @param url
     * @param view
     */
    public static void loadSquareImage(Context context, String url, View view) {
        RequestOptions options = new RequestOptions()
                .override(100, 100)
                .centerCrop();
        Glide.with(context).load(url).apply(options).into((ImageView) view);
    }


}
