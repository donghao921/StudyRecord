package com.dongh.funplus.utils;

import android.content.Context;
import android.widget.ImageView;

import com.dongh.baselib.glide.GlideHelper;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideHelper.loadImage(context, (String) path, imageView);
    }
}
