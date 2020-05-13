package com.cc.retrofitdemo.network;

import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.utils.LogInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainNetwork {
    private MainApi mMainApi;

    public static MainNetwork getInstance() {
        return MainNetwork.SingletonHolder.INSTANCE;
    }


    private MainNetwork() {
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(MainApi.BASE_URL).client(this.getDefaultClient()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        this.mMainApi = mRetrofit.create(MainApi.class);
    }

    private OkHttpClient getDefaultClient() {
        okhttp3.OkHttpClient.Builder builder = (new okhttp3.OkHttpClient.Builder()).connectTimeout(30L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS);
        builder.addInterceptor(new LogInterceptor());
        return builder.build();
    }

    private static class SingletonHolder {
        private static final MainNetwork INSTANCE = new MainNetwork();
    }


    public Observable<RetrofitBean> recommend() {
        return this.mMainApi.getRecommend();
    }

}
