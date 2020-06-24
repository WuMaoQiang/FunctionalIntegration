package com.cc.retrofitdemo.designmode.modes.proxy;

import com.cc.retrofitdemo.utils.LogUtils;

public class RealSubject implements Subject {
    private static final String TAG = "RealSubject";

    @Override
    public void sayGoodBye() {
        LogUtils.i(TAG, "RealSubject sayGoodBye  ");
    }

    @Override
    public void sayHello(String str) {
        LogUtils.i(TAG, "RealSubject sayHello  " + str);
    }
}
