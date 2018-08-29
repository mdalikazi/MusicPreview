package com.alikazi.codetestaim.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class AimViewUtils {

    public static void showImageWithGlide(Context context, String url, ImageView target) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .transition(new BitmapTransitionOptions().crossFade())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(target);
    }
}
