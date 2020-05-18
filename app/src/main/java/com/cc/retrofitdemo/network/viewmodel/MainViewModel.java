package com.cc.retrofitdemo.network.viewmodel;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.cc.retrofitdemo.network.bean.ArticleListBean;
import com.cc.retrofitdemo.network.bean.RemoteDataResource;
import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.bean.SearchResultBean;
import com.cc.retrofitdemo.network.bean.UserResponseBean;
import com.cc.retrofitdemo.network.reposity.RequestRepository;
import com.cc.retrofitdemo.network.utils.LogUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<RemoteDataResource> mWrapperLiveData = new MutableLiveData<>();
    private static final ConcurrentHashMap<String, Disposable> mRequestDisposables = new ConcurrentHashMap<>();
    private static final String KEY_CHAPTERS = "getChapters";
    private static final String KEY_ARTICLE_LIST = "articleList";
    private static final String KEY_DO_SEARCH = "doSearch";
    private static final String KEY_USER_LOGIN = "userLogin";

    private static void addDisposable(String key, Disposable disposable) {
        mRequestDisposables.put(key, disposable);
    }

    private static boolean ignoreSameDisposable(String key) {
        Disposable disposable = mRequestDisposables.get(key);
        if (disposable != null && !disposable.isDisposed()) {
            LogUtils.i(TAG, key + "_SameActionDisposable now ignore");
            return true;
        }
        return false;
    }

    private static void disposeOtherRunningDisposable(String key) {
        Set<String> keys = mRequestDisposables.keySet();
        for (String disposableKey : keys) {
            if (TextUtils.equals(key, disposableKey)) {
                continue;
            }
            Disposable disposable = mRequestDisposables.get(disposableKey);
            if (disposable != null && !disposable.isDisposed()) {
                LogUtils.i(TAG, "OtherRunningDisposable dispose");
                disposable.dispose();
            }
        }
    }

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
        if (ignoreSameDisposable(KEY_CHAPTERS)) return;
        disposeOtherRunningDisposable(KEY_CHAPTERS);
        Disposable disposable = RequestRepository.getInstance().getChapters().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<RetrofitBean, ObservableSource<RemoteDataResource<List<RetrofitBean.DataBean>>>>() {
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
        addDisposable(KEY_CHAPTERS, disposable);
    }

    @SuppressLint("CheckResult")
    public void requestArticleList(String itemId) {
        if (ignoreSameDisposable(KEY_ARTICLE_LIST)) return;
        disposeOtherRunningDisposable(KEY_ARTICLE_LIST);
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
        addDisposable(KEY_ARTICLE_LIST, disposable);
    }

    @SuppressLint("CheckResult")
    public void doSearch(String itemId, String key) {
        if (ignoreSameDisposable(KEY_DO_SEARCH)) return;
        disposeOtherRunningDisposable(KEY_DO_SEARCH);
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
                        return Observable.just(RemoteDataResource.success(datasBean));
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
        addDisposable(KEY_DO_SEARCH, disposable);

    }

    @SuppressLint("CheckResult")
    public void userLogin(String name, String password) {
        if (ignoreSameDisposable(KEY_USER_LOGIN)) return;
        disposeOtherRunningDisposable(KEY_USER_LOGIN);
        Disposable disposable = RequestRepository.getInstance().userLogin(name, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<UserResponseBean, ObservableSource<RemoteDataResource<UserResponseBean>>>() {
            @Override
            public ObservableSource<RemoteDataResource<UserResponseBean>> apply(UserResponseBean userResponseBean) throws Exception {
                if (userResponseBean != null) {
                    return Observable.create(emitter -> {
                        emitter.onNext(RemoteDataResource.success(userResponseBean));
                        emitter.onComplete();
                    });
                }
                return Observable.error(new IndexOutOfBoundsException());
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
        addDisposable(KEY_USER_LOGIN, disposable);
    }

    /**
     * Test All Rxjava operator symbol   ==============================================================
     */
    public void differ() {
        // 通过defer 定义被观察者对象 注：此时被观察者对象还没创建,当调用subscribe 才创建，此时时间戳为最新值
        Observable<Long> observable = Observable.defer(new Callable<ObservableSource<? extends Long>>() {
            @Override
            public ObservableSource<? extends Long> call() throws Exception {
                LogUtils.d(TAG, "Observable创建的时间戳: " + System.currentTimeMillis());
                return Observable.just(System.currentTimeMillis());
            }
        });

        LogUtils.d(TAG, "observable.subscribe的时间戳: " + System.currentTimeMillis());
        //观察者开始订阅 注：此时，才会调用defer() 创建被观察者对象(Observable)
        observable.subscribe(new Observer<Long>() {

            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Long value) {
                LogUtils.d(TAG, "接收到的时间戳是" + value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    @SuppressLint("CheckResult")
    public void timer() {
        LogUtils.d(TAG, "start time=" + System.currentTimeMillis());
        Observable.timer(2, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Long aLong) throws Exception {
                return Observable.just("aLong=" + aLong);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.d(TAG, "accept time=" + System.currentTimeMillis());
            }
        });
    }

    public void interval() {
        // 延迟3s后，每隔1秒发送1个事件, 产生1个数字（从0开始递增1，无限个）
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .take(5)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        LogUtils.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    public void intervalRange() {
        //延时3s后，每隔2s发送一个事件，事件序列：从5开始递增，总共发送10个事件
        //前四个参数含义为：long start, long count, long initialDelay, long period
        Observable.intervalRange(5, 10, 3, 2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    public void range() {
        // 从2开始发送10个事件, 每次发送的事件递增1
        // final int start, final int count
        // 注意：参数是int型的，count必须大于0，且满足start + (count - 1) <= Integer.MAX_VALUE
        Observable.range(2, 10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    public void rangeLong() {
        // 从2开始发送10个事件, 每次发送的事件递增1
        // long start, long count
        // 注意：参数是long型的，count必须大于0，且满足start + (count - 1) <= Long.MAX_VALUE
        Observable.rangeLong(2, 10)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    public void other() {
        // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成,
        // 即观察者接收后会直接调用onCompleted（）不会调用onNext
        Observable observable1 = Observable.empty();
//        observable1.subscribe(observer);

        // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常,
        // 即观察者接收后会直接调用onError（）
        Observable observable2 = Observable.error(new RuntimeException());
//        observable2.subscribe(observer);

        // 该方法创建的被观察者对象发送事件的特点：不发送任何事件, 即观察者接收后不会调用onComplete onNext
        Observable observable3 = Observable.never();
//        observable3.subscribe(observer);
    }

    @SuppressLint("CheckResult")
    public void map() {
        Observable.just(5, 6, 7).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "IntegerToString=" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.i(TAG, s);
            }
        });
    }

    /**
     * Test All Rxjava operator symbol   ==============================================================
     */
}
