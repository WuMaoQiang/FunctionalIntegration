package com.cc.retrofitdemo;

import android.app.Application;

import com.cc.retrofitdemo.crash.CrashHandler;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(this);

    }
}
