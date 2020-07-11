package com.cc.retrofitdemo.musicplayer.client;

import android.os.Handler;

import com.cc.retrofitdemo.utils.LogUtils;

import androidx.lifecycle.MutableLiveData;


public final class MusicProgressLiveData extends MutableLiveData<Boolean> {
    private static final String TAG = "MusicProgressLiveData";

    private static final long UPDATE_INTERNAL = 500;

    private static MusicProgressLiveData mInstance;

    private Handler mUpdateHandler = new Handler();

    private Runnable mUpdateRunnable = () -> {
        if (!MusicController.getInstance().isPlaying()) {
            LogUtils.i(TAG, "not playing");
            return;
        }
        setValue(true);
        sendUpdateAction();
    };

    public static MusicProgressLiveData getInstance() {
        if (null == mInstance) {
            mInstance = new MusicProgressLiveData();
        }
        return mInstance;
    }

    @Override
    protected void onActive() {
        super.onActive();
        sendUpdateAction();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        cancelUpdateAction();
    }

    public void sendUpdateAction() {
        mUpdateHandler.postDelayed(mUpdateRunnable, UPDATE_INTERNAL);
    }

    public void cancelUpdateAction() {
        mUpdateHandler.removeCallbacks(mUpdateRunnable);
    }


}
