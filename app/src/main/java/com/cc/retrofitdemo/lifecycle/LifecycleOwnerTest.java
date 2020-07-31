package com.cc.retrofitdemo.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;


public class LifecycleOwnerTest implements LifecycleOwner {
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    public void onCreate() {
        getLifecycle().addObserver(new LifecycleObserverTest());
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }
}
