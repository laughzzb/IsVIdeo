package com.is.libbase.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class GlideUtils {
    /**
     * 加载视频封面到imageView
     * @param url
     * @param imageView
     */

    public static void loadImage(String url, ImageView imageView){

        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    /**
     * 加载圆形图像到ImageView
     * @param imageView
     */
    public static void loadCircleImage(String url, ImageView imageView){

        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))//加载圆形头像图片
                .into(imageView);
    }
    public static void loadImage(File file, ImageView imageView){

        Glide.with(imageView.getContext())
                .load(file)
                .into(imageView);
    }

}
