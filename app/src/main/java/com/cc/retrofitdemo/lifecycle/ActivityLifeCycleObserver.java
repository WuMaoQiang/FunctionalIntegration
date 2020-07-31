package com.cc.retrofitdemo.lifecycle;

import com.cc.retrofitdemo.utils.LogUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;


public class ActivityLifeCycleObserver implements LifecycleObserver {
    private static final String TAG = "MyLifeCycleObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onStart(LifecycleOwner owner) {
        LogUtils.i(TAG, "onStart");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        LogUtils.i(TAG, "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
        LogUtils.i(TAG, "onResume");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(LifecycleOwner owner) {
        LogUtils.i(TAG, "onPause");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(LifecycleOwner owner) {
        LogUtils.i(TAG, "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        LogUtils.i(TAG, "onDestroy");
    }

//    LifecycleEventObserver
//    @Override
//    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
//        LogUtils.i(TAG,"change=="+event.name());
//
//    }
}
