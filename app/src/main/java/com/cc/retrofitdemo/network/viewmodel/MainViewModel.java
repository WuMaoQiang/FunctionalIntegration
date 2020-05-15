package com.cc.retrofitdemo.network.viewmodel;

import android.annotation.SuppressLint;


import com.cc.retrofitdemo.network.bean.RemoteDataResource;
import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.reposity.RequestRepository;
import com.cc.retrofitdemo.network.utils.LogUtils;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<RemoteDataResource<List<RetrofitBean.DataBean>>> mWrapperLiveData = new MutableLiveData<>();

    public MainViewModel() {
    }

    public static MainViewModel getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MainViewModel INSTANCE = new MainViewModel();
    }

    public MutableLiveData<RemoteDataResource<List<RetrofitBean.DataBean>>> getData() {
        return this.mWrapperLiveData;
    }

    @SuppressLint("CheckResult")
    public void requestData() {
        RequestRepository.getInstance().getRecommend().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<RetrofitBean, ObservableSource<RemoteDataResource<List<RetrofitBean.DataBean>>>>() {
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
}
