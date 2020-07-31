package com.cc.retrofitdemo.lifecycle;

import android.os.Bundle;
import android.os.Handler;

import com.cc.retrofitdemo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LifeCycleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        //Activity的Lifecycle
        ActivityLifeCycleObserver observer = new ActivityLifeCycleObserver();
        getLifecycle().addObserver(observer);
        //Fragment的Lifecycle
        replaceFragment(new LifecycleFragment());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new LifecycleOwnerTest().onCreate();
            }
        }, 3000);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
