package com.cc.retrofitdemo.designmode.modes;

public class SingletonDesignMode {
    private static volatile SingletonDesignMode instance;

    public static SingletonDesignMode getInstance() {
        if (instance == null) {
            synchronized (SingletonDesignMode.class) {
                if (instance == null) {
                    instance = new SingletonDesignMode();
                }
            }
        }
        return instance;
    }
}
