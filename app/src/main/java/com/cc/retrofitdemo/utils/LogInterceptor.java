package com.cc.retrofitdemo.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LogInterceptor implements Interceptor {
    private static final String TAG = "LogInterceptor";

    public LogInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.d(TAG, "Http request url " + request.url());
        long time = System.currentTimeMillis();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        String responseBodyString = response.body().string();
        Response newResponse = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes())).build();
        LogUtils.d(TAG, "Http consumed " + (System.currentTimeMillis() - time));
        LogUtils.d(TAG, "Http response url " + response.request().url());
        LogUtils.d(TAG, "Http request body " + bodyToString(request));
        LogUtils.d(TAG, "Http response body " + responseBodyString);
        return newResponse;
    }

    private static String bodyToString(Request request) {
        Buffer buffer = new Buffer();

        String var3;
        try {
            Request copy = request.newBuilder().build();
            if (copy.body() != null) {
                copy.body().writeTo(buffer);
                var3 = buffer.readUtf8();
                return var3;
            }

            var3 = "";
        } catch (IOException var7) {
            var3 = "did not work";
            return var3;
        } finally {
            buffer.close();
        }

        return var3;
    }
}
