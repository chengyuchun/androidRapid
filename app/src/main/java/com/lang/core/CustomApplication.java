package com.lang.core;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.lang.BuildConfig;
import com.lang.utils.AppStatusTracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class CustomApplication extends Application {
    private static CustomApplication mInstance;
    private static Context mContext;
    private RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        AppStatusTracker.init(this);
        mRefWatcher = BuildConfig.DEBUG ?  LeakCanary.install(this) : RefWatcher.DISABLED;
    }

    public static Context getContextObject() {
        return mContext;
    }

    public static CustomApplication getInstance() {
        return mInstance;
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
