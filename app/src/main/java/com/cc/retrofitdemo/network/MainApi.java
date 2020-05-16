package com.cc.retrofitdemo.network;


import com.cc.retrofitdemo.network.bean.ArticleListBean;
import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.bean.SearchResultBean;
import com.cc.retrofitdemo.network.bean.UserResponseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * https://api.github.com的格式可以看成scheme://host[:port]（此种类型是不是以 /（斜线） 结尾都可以，均不会抛出IllegalArgumentException异常）;
 * https://api.github.com/repos/的格式可以看成scheme://host[:port]/path（此种类型必须以/（斜线） 结尾，否则会抛出IllegalArgumentException异常）.
 */
public interface MainApi {
    String BASE_URL = "https://wanandroid.com/";


    @GET("wxarticle/chapters/json")
    Observable<RetrofitBean> getRecommend();

    @GET("article/list/{itemId}/json")
    Observable<ArticleListBean> getArticleList(@Path("itemId") String itemId);

    @FormUrlEncoded
    @POST("article/query/{itemId}/json")
    Observable<SearchResultBean> doSearch(@Path("itemId") String itemId, @Field("k") String key);

    @FormUrlEncoded
    @POST("user/login")
    Observable<UserResponseBean> userLogin(@Field("username") String username, @Field("password") String password);

    @POST("user/login")
    Observable<UserResponseBean> userLogin(@Body RequestBody requestBody);
}
