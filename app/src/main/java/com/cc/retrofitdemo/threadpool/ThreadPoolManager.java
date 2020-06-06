package com.cc.retrofitdemo.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 20;
    private static final long KEEP_ALIVE_TIME = 10L;
    private static volatile ThreadPoolManager instance = null;


    private static ThreadPoolExecutor mExecutorService = null;

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    private synchronized ExecutorService executorService() {
        if (isThreadServiceEnable()) {
            mExecutorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                    KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(5),
                    new MyThreadFactory("wilfried"), new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return mExecutorService;
    }

    private boolean isThreadServiceEnable() {
        return !(mExecutorService == null
                || mExecutorService.isShutdown() || mExecutorService.isTerminated());
    }

    public synchronized void doSubmit(Runnable runnable) {
        executorService().submit(runnable);
    }

    public synchronized void shutDown() {
        if (isThreadServiceEnable()) {
            executorService().shutdown();
        }
    }
}
