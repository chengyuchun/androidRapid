package com.lang.utils;

import android.util.Log;

import com.lang.BuildConfig;

public class L {
    private static final String TAG = "lang";
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String msg) {
        if (DEBUG){
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG){
            Log.e(TAG, msg);
        }
    }
}
