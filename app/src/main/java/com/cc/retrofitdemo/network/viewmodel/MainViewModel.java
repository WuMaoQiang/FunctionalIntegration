package com.cc.retrofitdemo.network.viewmodel;

import android.annotation.SuppressLint;


import com.cc.retrofitdemo.network.bean.RetrofitBean;
import com.cc.retrofitdemo.network.reposity.RequestRepository;
import com.cc.retrofitdemo.network.utils.LogUtils;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<RetrofitBean> mWrapperLiveData = new MutableLiveData<>();

    public MainViewModel() {
    }

    public static MainViewModel getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MainViewModel INSTANCE = new MainViewModel();
    }

    public MutableLiveData<RetrofitBean> getData() {
        return this.mWrapperLiveData;
    }

    @SuppressLint("CheckResult")
    public void requestData() {
        RequestRepository.getInstance().getRecommend().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((RetrofitBean baseResponseBean) -> {
            if (baseResponseBean != null) {
                LogUtils.i(TAG, "accept: " + baseResponseBean.toString());
                mWrapperLiveData.postValue(baseResponseBean);
            }
        }, throwable -> LogUtils.i(TAG, "accept: " + throwable.toString()));
    }
}
