package com.cc.retrofitdemo.utils;

import android.content.Context;

public class ContextProvider {
    private static Context mAppContext;

    public static void init(Context ctx) {
        mAppContext = ctx;
    }

    public static Context getApplication() {
        return mAppContext;
    }
}
