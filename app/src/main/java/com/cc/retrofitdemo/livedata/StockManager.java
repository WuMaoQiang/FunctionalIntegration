package com.cc.retrofitdemo.livedata;

import com.cc.retrofitdemo.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


public class StockManager {
    private SimplePriceListener mSimplePriceListener;

    public void requestPriceUpdates(SimplePriceListener simplePriceListener) {
        this.mSimplePriceListener = simplePriceListener;
        AtomicInteger count = new AtomicInteger();


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mSimplePriceListener!=null){
                    mSimplePriceListener.onPriceChanged(count.get());
                    count.getAndIncrement();
                }
            }
        }, 1000, 2000);
    }

    public void removeUpdates() {
        mSimplePriceListener = null;
    }

    public interface SimplePriceListener {
        void onPriceChanged(int price);
    }
}
