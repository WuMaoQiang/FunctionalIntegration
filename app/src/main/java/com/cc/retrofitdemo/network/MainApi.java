package com.cc.retrofitdemo.network;


import com.cc.retrofitdemo.network.bean.RetrofitBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * https://api.github.com的格式可以看成scheme://host[:port]（此种类型是不是以 /（斜线） 结尾都可以，均不会抛出IllegalArgumentException异常）;
 * https://api.github.com/repos/的格式可以看成scheme://host[:port]/path（此种类型必须以/（斜线） 结尾，否则会抛出IllegalArgumentException异常）.
 */
public interface MainApi {
    String BASE_URL = "https://wanandroid.com/";


    @GET("wxarticle/chapters/json")
    Observable<RetrofitBean> getRecommend();
}
