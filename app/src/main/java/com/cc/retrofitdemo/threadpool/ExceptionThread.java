package com.cc.retrofitdemo.threadpool;

public class ExceptionThread implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException("抛出运行时异常");
    }
}