package com.cc.retrofitdemo.networkkotlin

import com.cc.retrofitdemo.network.MainApi
import com.cc.retrofitdemo.network.bean.UserResponseBean
import com.cc.retrofitdemo.utils.LogInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MainNetWorkKotlin {
    private val mMainApi: MainApi = Retrofit.Builder().baseUrl(MainApi.BASE_URL)
            .client(getDefaultClient()).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(MainApi::class.java)


    private fun getDefaultClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().connectTimeout(30L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS)
        builder.addInterceptor(LogInterceptor())
        return builder.build()
    }

    fun userLogin(name: String, password: String): Observable<UserResponseBean> = this.mMainApi.userLogin(name, password);
}