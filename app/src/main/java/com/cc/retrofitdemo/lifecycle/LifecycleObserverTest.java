package com.cc.retrofitdemo.lifecycle;

import com.cc.retrofitdemo.utils.LogUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


public class LifecycleObserverTest implements LifecycleObserver {
    private static final String TAG = "LifecycleObserverTest";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate() {
        LogUtils.i(TAG, "onCreate");

    }
}
