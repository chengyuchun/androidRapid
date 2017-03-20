package com.lang.core;

import android.app.Application;
import android.content.Context;

import com.framework.util.AppStatusTracker;

public class CustomApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        AppStatusTracker.init(this);
    }

    public static Context getContextObject() {
        return mContext;
    }
}
