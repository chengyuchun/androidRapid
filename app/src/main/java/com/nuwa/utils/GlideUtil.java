package com.nuwa.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nuwa.R;

/**
 * Created by chengyuchun on 2016/11/9.
 */
public class GlideUtil {
    public static void displayImg(ImageView imageView,String url){
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.color.app_primary_color)
                .crossFade()
                .into(imageView);
    }
}
