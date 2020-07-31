package com.cc.retrofitdemo.lifecycle;

import com.cc.retrofitdemo.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class FragmentLifeCycleObserver implements LifecycleEventObserver {
    private static final String TAG = "FragmentLifeCycleObserv";

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        LogUtils.i(TAG, "event==" + event.name());

    }
}
