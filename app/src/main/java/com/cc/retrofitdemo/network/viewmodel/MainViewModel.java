package com.cc.retrofitdemo.network.viewmodel;

import android.annotation.SuppressLint;


import com.cc.retrofitdemo.network.bean.ArticleListBean;
import com.cc.retrofitdemo.network.bean.RemoteDataResource;
import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.bean.SearchResultBean;
import com.cc.retrofitdemo.network.bean.UserResponseBean;
import com.cc.retrofitdemo.network.reposity.RequestRepository;
import com.cc.retrofitdemo.network.utils.LogUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<RemoteDataResource> mWrapperLiveData = new MutableLiveData<>();

    public MainViewModel() {
    }

    public static MainViewModel getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MainViewModel INSTANCE = new MainViewModel();
    }

    public MutableLiveData<RemoteDataResource> getData() {
        return this.mWrapperLiveData;
    }

    @SuppressLint("CheckResult")
    public void requestData() {
        Disposable disposable = RequestRepository.getInstance().getRecommend().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<RetrofitBean, ObservableSource<RemoteDataResource<List<RetrofitBean.DataBean>>>>() {
            @Override
            public ObservableSource<RemoteDataResource<List<RetrofitBean.DataBean>>> apply(RetrofitBean retrofitBean) throws Exception {
                return Observable.just(RemoteDataResource.success(retrofitBean.getData()));
            }
        }).subscribe(new Consumer<RemoteDataResource<List<RetrofitBean.DataBean>>>() {
            @Override
            public void accept(RemoteDataResource<List<RetrofitBean.DataBean>> baseResponseBean) throws Exception {
                if (baseResponseBean != null) {
                    LogUtils.i(TAG, "accept: " + baseResponseBean.result);
                    mWrapperLiveData.postValue(baseResponseBean);
                }
            }
        }, throwable -> LogUtils.i(TAG, "accept: " + throwable.toString()));
    }

    @SuppressLint("CheckResult")
    public void requestArticleList(String itemId) {
        Disposable disposable = RequestRepository.getInstance().getArticleList(itemId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<ArticleListBean, ObservableSource<RemoteDataResource<ArticleListBean>>>() {
            @Override
            public ObservableSource<RemoteDataResource<ArticleListBean>> apply(ArticleListBean articleListBean) throws Exception {
                return Observable.just(RemoteDataResource.success(articleListBean));
            }
        }).subscribe(new Consumer<RemoteDataResource<ArticleListBean>>() {
            @Override
            public void accept(RemoteDataResource<ArticleListBean> articleListBean) throws Exception {
                if (articleListBean != null) {
                    LogUtils.i(TAG, "accept: " + articleListBean.toString());
                    mWrapperLiveData.postValue(articleListBean);
                }
            }
        }, throwable -> LogUtils.i(TAG, "accept: " + throwable.toString()));
    }

    @SuppressLint("CheckResult")
    public void doSearch(String itemId, String key) {
        Disposable disposable = RequestRepository.getInstance().doSearch(itemId, key).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<SearchResultBean, ObservableSource<SearchResultBean.DataBean.DatasBean>>() {
                    @Override
                    public ObservableSource<SearchResultBean.DataBean.DatasBean> apply(SearchResultBean searchResultBean) throws Exception {
                        return Observable.fromIterable(searchResultBean.getData().getDatas());
                    }
                }).take(1)
                .flatMap(new Function<SearchResultBean.DataBean.DatasBean, ObservableSource<RemoteDataResource<SearchResultBean.DataBean.DatasBean>>>() {
                    @Override
                    public ObservableSource<RemoteDataResource<SearchResultBean.DataBean.DatasBean>> apply(SearchResultBean.DataBean.DatasBean datasBean) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<RemoteDataResource<SearchResultBean.DataBean.DatasBean>>() {
                            @Override
                            public void subscribe(ObservableEmitter<RemoteDataResource<SearchResultBean.DataBean.DatasBean>> emitter) throws Exception {
                                emitter.onNext(RemoteDataResource.success(datasBean));
                                emitter.onComplete();
                            }
                        });
                    }
                }).subscribe(new Consumer<RemoteDataResource<SearchResultBean.DataBean.DatasBean>>() {
                    @Override
                    public void accept(RemoteDataResource<SearchResultBean.DataBean.DatasBean> remoteDataResource) throws Exception {
                        if (remoteDataResource != null) {
                            LogUtils.i(TAG, "accept: " + remoteDataResource.toString());
                            mWrapperLiveData.postValue(remoteDataResource);
                        }
                    }
                }, throwable -> LogUtils.i(TAG, "accept: " + throwable.toString()));
    }

    @SuppressLint("CheckResult")
    public void userLogin(String name, String password) {
        Disposable disposable = RequestRepository.getInstance().userLogin(name, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<UserResponseBean, ObservableSource<RemoteDataResource<UserResponseBean>>>() {
            @Override
            public ObservableSource<RemoteDataResource<UserResponseBean>> apply(UserResponseBean userResponseBean) throws Exception {
                return Observable.create(emitter -> {
                    emitter.onNext(RemoteDataResource.success(userResponseBean));
                    emitter.onComplete();
                });
            }
        }).subscribe(new Consumer<RemoteDataResource<UserResponseBean>>() {
            @Override
            public void accept(RemoteDataResource<UserResponseBean> userResponseBeanRemoteDataResource) throws Exception {
                if (userResponseBeanRemoteDataResource != null) {
                    LogUtils.i(TAG, "accept: " + userResponseBeanRemoteDataResource.toString());
                    mWrapperLiveData.postValue(userResponseBeanRemoteDataResource);
                }
            }
        }, throwable -> LogUtils.i(TAG, "accept: " + throwable.toString()));
    }


}
