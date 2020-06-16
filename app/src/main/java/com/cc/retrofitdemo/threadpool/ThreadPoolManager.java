package com.cc.retrofitdemo.threadpool;

import com.cc.retrofitdemo.network.utils.LogUtils;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static final String TAG = "ThreadPoolManager";
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

    /**
     * 1、   BlockingQueue
     * ArrayBlockingQueue
     * LinkedBlockingQueue
     * SynchronousQueue
     * DelayQueue
     * 2、  RejectedExecutionHandler
     * AbortPolicy          抛出异常 RejectedExecutionException
     * DiscardOldestPolicy  丢弃等待时间最长的任务（removes the head of this queue）
     * DiscardPolicy        丢弃任务 （Does nothing）
     * CallerRunsPolicy     用调用者所在的线程来处理任务
     *
     * @return ExecutorService
     */
    private synchronized ExecutorService executorService() {
        if (!isThreadServiceEnable()) {
            mExecutorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                    KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5),
                    new MyThreadFactory("wilfried"), new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return mExecutorService;
    }

    private boolean isThreadServiceEnable() {
        return !(mExecutorService == null
                || mExecutorService.isShutdown() || mExecutorService.isTerminated());
    }

    public synchronized void doSubmit(Runnable runnable) {
        //注意submit和execute的区别：submit不会直接报异常而是封装到Future中
        Future<?> future = executorService().submit(runnable);
        try {
            if (future.get() == null) {
                LogUtils.i(TAG, "任务完成");
            }
        } catch (Exception e) {
            //任务失败的原因是什么
            LogUtils.e(TAG, Thread.currentThread().getName()+".."+ Objects.requireNonNull(e.getCause()).getMessage());
        }
    }

    public synchronized void shutDown() {
        if (isThreadServiceEnable()) {
            executorService().shutdown();
        }
    }
}
