package com.cc.retrofitdemo.musicplayer.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.cc.retrofitdemo.R;
import com.cc.retrofitdemo.musicplayer.player.IPlayController;
import com.cc.retrofitdemo.musicplayer.player.MediaPlayerImpl;
import com.cc.retrofitdemo.utils.ContextProvider;
import com.cc.retrofitdemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.media.MediaBrowserServiceCompat;

@SuppressLint("Registered")
public class MusicService extends MediaBrowserServiceCompat {
    private static final String TAG = "MusicService";
    private static final String BROWSER_ROOT = "BrowserRoot";
    private IPlayController mCurPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        initMusicService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void initMusicService() {
        LogUtils.i(TAG, "MusicService Start");
        adaptAndroidOService();
        MediaSessionCompat mSession = new MediaSessionCompat(this, "MusicService");
        this.setSessionToken(mSession.getSessionToken());
        mSession.setCallback(new MediaSessionCallback());
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
                | MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS);
        mCurPlayer = new MediaPlayerImpl(ContextProvider.getApplication());

        mCurPlayer.setCallback(new IPlayController.Callback() {
            @Override
            public void onCompletion(boolean var1) {

            }

            @Override
            public void onPlaybackStatusChanged(int state) {
                LogUtils.i(TAG, "onPlaybackStatusChanged" + state);

                PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                        .setActions(getAvailableActions());

                stateBuilder.setState(state, 122, 1.0f, SystemClock.elapsedRealtime());

                try {
                    mSession.setPlaybackState(stateBuilder.build());
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int var1, String var2) {

            }

            @Override
            public void onProcessUpdate(long var1, long var3) {

            }
        });

    }


    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            LogUtils.i(TAG, "onPlay");
            if (mCurPlayer.getPlayableMedia() == null) {
                mCurPlayer.setPlayableMedia();
            } else {
                mCurPlayer.start();
            }
        }

        @Override
        public void onPause() {
            super.onPause();
            LogUtils.i(TAG, "onPause");
            mCurPlayer.pause();

        }

        @Override
        public void onRewind() {
            super.onRewind();
            LogUtils.i(TAG, "onRewind");
        }

        @Override
        public void onFastForward() {
            super.onFastForward();
            LogUtils.i(TAG, "onFastForward");

        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            LogUtils.i(TAG, "onSkipToNext");
            mCurPlayer.setPlayableMedia();

        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            LogUtils.i(TAG, "onSkipToPrevious");
            mCurPlayer.setPlayableMedia();

        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            LogUtils.i(TAG, "onSeekTo");

        }

        @Override
        public void onSetRepeatMode(int repeatMode) {
            super.onSetRepeatMode(repeatMode);
            LogUtils.i(TAG, "onSetRepeatMode");

        }

        @Override
        public void onSetShuffleMode(int shuffleMode) {
            super.onSetShuffleMode(shuffleMode);
            LogUtils.i(TAG, "onSetShuffleMode");

        }

        @Override
        public void onStop() {
            super.onStop();
            LogUtils.i(TAG, "onStop");
        }
    }


    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new MediaBrowserServiceCompat.BrowserRoot(BROWSER_ROOT, null);

    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(new ArrayList<>());
    }

    /**
     * 在android 8.0 禁止启动后台服务。
     * 通过startForegroundService() 启动前台服务。
     * 但是必须要配合在service 中调用Service.startForeground()，不然就会出现ANR 或者crash。
     */
    private void adaptAndroidOService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            try {
                Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("")
                        .setContentText("").build();

                startForeground(1, notification);
            } catch (Exception e) {
            }
        }
    }

    private long getAvailableActions() {
        long actions =
                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                        PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
        if (mCurPlayer != null && mCurPlayer.isPlaying()) {
            actions |= PlaybackStateCompat.ACTION_PAUSE;
        } else {
            actions |= PlaybackStateCompat.ACTION_PLAY;
        }
        return actions;
    }
}
