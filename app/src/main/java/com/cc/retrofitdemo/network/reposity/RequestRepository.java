package com.cc.retrofitdemo.network.reposity;

import com.cc.retrofitdemo.network.MainNetwork;
import com.cc.retrofitdemo.network.bean.RetrofitBean;

import io.reactivex.Observable;

public class RequestRepository {

    public static RequestRepository getInstance() {
        return RequestRepository.Holder.sInstance;
    }

    private static class Holder {
        private static RequestRepository sInstance = new RequestRepository();
    }

    public Observable<RetrofitBean> getRecommend() {
        return MainNetwork.getInstance().recommend();
    }

    // ......
}
