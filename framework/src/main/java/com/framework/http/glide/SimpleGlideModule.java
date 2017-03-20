package com.framework.http.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.framework.http.ApiWrapper;

import java.io.InputStream;

public class SimpleGlideModule implements GlideModule {
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat( DecodeFormat.PREFER_ARGB_8888 );
    }

    @Override public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ApiWrapper.getInstance().getOkHttpClient()));
    }
}