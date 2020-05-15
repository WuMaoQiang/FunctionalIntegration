package com.cc.retrofitdemo.network.bean;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RemoteDataResource<OriginalData> {
    @NonNull
    public final DataResult result;

    public OriginalData data;

    public String message;

    public Throwable throwable;

    public RemoteDataResource(@NonNull DataResult result) {
        this.result = result;
    }

    public RemoteDataResource(@NonNull DataResult result, @Nullable OriginalData data) {
        this.result = result;
        this.data = data;
    }

    public RemoteDataResource(@NonNull DataResult result, @NonNull String message) {
        this.result = result;
        this.message = message;
    }


    public RemoteDataResource(@NonNull DataResult result, @NonNull Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }

    public static <OriginalData> RemoteDataResource<OriginalData> success(@NonNull OriginalData data) {
        return new RemoteDataResource<>(DataResult.SUCCESS, data);
    }

    public static <OriginalData> RemoteDataResource<OriginalData> empty() {
        return new RemoteDataResource<>(DataResult.EMPTY);
    }

    public static <OriginalData> RemoteDataResource<OriginalData> error(@NonNull String message) {
        return new RemoteDataResource<>(DataResult.ERROR, message);
    }

    public static <OriginalData> RemoteDataResource<OriginalData> netError(@NonNull Throwable throwable) {
        return new RemoteDataResource<>(DataResult.NET_ERROR, throwable);
    }

    public static <OriginalData> RemoteDataResource<OriginalData> exception(@NonNull Throwable throwable) {
        return new RemoteDataResource<>(DataResult.EXCEPTION, throwable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoteDataResource<?> that = (RemoteDataResource<?>) o;
        return result == that.result &&
                Objects.equals(data, that.data) &&
                Objects.equals(throwable, that.throwable);
    }

    @Override
    public String toString() {
        return "RemoteDataResource{" +
                "result=" + result +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                '}';
    }

}