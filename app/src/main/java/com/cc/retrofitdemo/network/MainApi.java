package com.cc.retrofitdemo.network;


import com.cc.retrofitdemo.network.bean.RetrofitBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MainApi {
    String BASE_URL = "https://wanandroid.com/";


    @GET("wxarticle/chapters/json")
    Observable<RetrofitBean> getRecommend();
}
