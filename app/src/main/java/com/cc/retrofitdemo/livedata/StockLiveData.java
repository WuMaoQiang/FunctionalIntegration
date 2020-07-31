package com.cc.retrofitdemo.livedata;

import com.cc.retrofitdemo.utils.LogUtils;

import androidx.lifecycle.LiveData;


public class StockLiveData extends LiveData<Integer> {
    private static final String TAG = "StockLiveData";
    private StockManager stockManager;

    private StockManager.SimplePriceListener listener = price -> {
        LogUtils.i(TAG, "p==" + price);
        postValue(price);
    };

    StockLiveData() {
        stockManager = new StockManager();
    }

    @Override
    protected void onActive() {
        super.onActive();
        LogUtils.i(TAG, "onActive");
        stockManager.requestPriceUpdates(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        LogUtils.i(TAG, "onInactive");
        stockManager.removeUpdates();
    }
}
