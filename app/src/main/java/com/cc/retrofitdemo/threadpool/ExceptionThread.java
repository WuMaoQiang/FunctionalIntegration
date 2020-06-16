package com.cc.retrofitdemo.threadpool;

import com.cc.retrofitdemo.network.utils.LogUtils;

public class ExceptionThread implements Runnable {
    private static final String TAG = "ExceptionThread";

    @Override
    public void run() {
        LogUtils.e(TAG, Thread.currentThread().getName() + "..");
        throw new RuntimeException("抛出运行时异常");
    }
}