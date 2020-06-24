package com.cc.retrofitdemo;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.cc.retrofitdemo.crash.CrashHandler;
import com.cc.retrofitdemo.musicplayer.service.MusicService;
import com.cc.retrofitdemo.utils.ContextProvider;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(this);
        ContextProvider.init(this);
        initMusicService();
    }

    private void initMusicService() {
        if (Build.VERSION.SDK_INT > 26) {
            startForegroundService(new Intent(this, MusicService.class));
        } else {
            startService(new Intent(this, MusicService.class));
        }
    }
}
