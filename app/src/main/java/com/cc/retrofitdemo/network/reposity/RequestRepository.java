package com.cc.retrofitdemo.network.reposity;

import com.cc.retrofitdemo.network.MainNetwork;
import com.cc.retrofitdemo.network.bean.ArticleListBean;
import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.bean.SearchResultBean;
import com.cc.retrofitdemo.network.bean.UserResponseBean;
import com.cc.retrofitdemo.networkkotlin.MainNetWorkKotlin;

import io.reactivex.Observable;

public class RequestRepository {

    public static RequestRepository getInstance() {
        return RequestRepository.Holder.sInstance;
    }

    private static class Holder {
        private static RequestRepository sInstance = new RequestRepository();
    }

    public Observable<RetrofitBean> getChapters() {
        return MainNetwork.getInstance().getChapters();
    }

    public Observable<ArticleListBean> getArticleList(String itemId) {
        return MainNetwork.getInstance().getArticleList(itemId);
    }

    public Observable<SearchResultBean> doSearch(String itemId, String key) {
        return MainNetwork.getInstance().doSearch(itemId, key);
    }

    public Observable<UserResponseBean> userLogin(String name, String password) {
        return MainNetWorkKotlin.INSTANCE.userLogin(name, password);
    }

    // ......
}
