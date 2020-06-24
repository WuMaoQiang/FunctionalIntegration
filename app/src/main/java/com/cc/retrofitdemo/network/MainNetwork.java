package com.cc.retrofitdemo.network;

import com.cc.retrofitdemo.network.bean.ArticleListBean;
import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.bean.SearchResultBean;
import com.cc.retrofitdemo.network.bean.UserResponseBean;
import com.cc.retrofitdemo.utils.LogInterceptor;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainNetwork {
    private MainApi mMainApi;
    private Gson mGson;

    public static MainNetwork getInstance() {
        return MainNetwork.SingletonHolder.INSTANCE;
    }


    private MainNetwork() {
        this.mGson = new Gson();
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


    public Observable<RetrofitBean> getChapters() {
        return this.mMainApi.getChapters();
    }

    public Observable<ArticleListBean> getArticleList(String itemId) {
        return this.mMainApi.getArticleList(itemId);
    }

    public Observable<SearchResultBean> doSearch(String itemId, String key) {
        return this.mMainApi.doSearch(itemId, key);
    }

    public Observable<UserResponseBean> userLogin(String name, String password) {
//        UserBean bean = new UserBean(name, password);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), this.mGson.toJson(bean));
//        Json形式body不一样
//        Http response url https://wanandroid.com/user/login
//        Http request body {"password":"xxxx","username":"xxxx@vip.qq.com"}
//        Http response body {"data":null,"errorCode":-1,"errorMsg":"账号密码不匹配！"}
//
//        接口仅支持表单形式
//        Http response url https://wanandroid.com/user/login
//        Http request body username=xxxx@vip.qq.com&password=xxx

        return this.mMainApi.userLogin(name, password);
//        return this.mMainApi.userLogin(requestBody);

    }

}
