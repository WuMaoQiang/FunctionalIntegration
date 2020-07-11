package com.cc.retrofitdemo.musicplayer.client;

import android.content.ComponentName;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.cc.retrofitdemo.musicplayer.service.MusicService;
import com.cc.retrofitdemo.utils.ContextProvider;
import com.cc.retrofitdemo.utils.LogUtils;

import java.util.List;

import androidx.annotation.NonNull;


public class MusicController {
    private static final String TAG = "MusicController";
    private static volatile MusicController mInstance;
    private MediaBrowserCompat mediaBrowserCompat;
    private MediaControllerCompat mMediaController;
    private boolean mIsPlaying = false;
    private MusicControllerCallback mMusicControllerCallback;

    public static MusicController getInstance() {
        if (mInstance == null) {
            synchronized (MusicController.class) {
                if (mInstance == null) {
                    mInstance = new MusicController();
                }
            }
        }

        return mInstance;
    }

    private MusicController() {
        LogUtils.i(TAG, "MediaBrowserCompat Create");
        mediaBrowserCompat = new MediaBrowserCompat(ContextProvider.getApplication(),
                new ComponentName(ContextProvider.getApplication(), MusicService.class),
                mConnectionCallback, null);
        mediaBrowserCompat.connect();

    }

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    LogUtils.i(TAG, "MediaBrowserCompat onConnected");
                    try {
                        connectToSession(mediaBrowserCompat.getSessionToken());
                    } catch (RemoteException ignored) {
                    }
                }
            };

    private void connectToSession(MediaSessionCompat.Token sessionToken) throws RemoteException {
        if (mMediaController == null) {
            mMediaController = new MediaControllerCompat(ContextProvider.getApplication(), sessionToken);
        }
        mMediaController.registerCallback(mMediaControllerCallback);
    }

    private MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            LogUtils.i(TAG, "onPlaybackStateChanged " + state.getState());
            mIsPlaying = (state.getState() == PlaybackStateCompat.STATE_PLAYING
                    || state.getState() == PlaybackStateCompat.STATE_BUFFERING
                    || state.getState() == PlaybackStateCompat.STATE_CONNECTING);
            notifyPlayStateChanged(mIsPlaying);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            LogUtils.i(TAG, "onMetadataChanged " + metadata);


        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            LogUtils.i(TAG, "onRepeatModeChanged " + repeatMode);

        }

        @Override
        public void onShuffleModeChanged(int shuffleMode) {
            LogUtils.i(TAG, "onShuffleModeChanged " + shuffleMode);
        }
    };

    private void notifyPlayStateChanged(boolean mIsPlaying) {

        mMusicControllerCallback.onPlayingStateChanged(mIsPlaying);
    }


    /**
     * 播放控制
     */

    public void play() {
        if (mMediaController != null) {
            mMediaController.getTransportControls().play();
        }
    }

    public void pause() {
        if (mMediaController != null) {
            mMediaController.getTransportControls().pause();
        }
    }

    public void playPrev() {
        if (mMediaController != null) {
            mMediaController.getTransportControls().skipToPrevious();
        }
    }

    public void playNext() {
        if (mMediaController != null) {
            mMediaController.getTransportControls().skipToNext();
        }
    }

    public void setRepeatMode(int repeatMode) {
        if (mMediaController != null) {
            mMediaController.getTransportControls().setRepeatMode(repeatMode);
        }

    }

    public void setShuffleMode(int shuffleMode) {
        if (mMediaController != null) {
            mMediaController.getTransportControls().setShuffleMode(shuffleMode);
        }
    }

    public void stop() {
        if (mMediaController != null) {
            mMediaController.getTransportControls().stop();
        }
    }

    public void seekTo(long position) {
        if (mMediaController != null) {
            mMediaController.getTransportControls().seekTo(position);
        }
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public int getCurrentPosition() {
        long position = 0;
        if (mMediaController != null) {
            if (mMediaController.getPlaybackState() != null) {
                position = mMediaController.getPlaybackState().getPosition();
            }
        }
        return (int) position;
    }


    public interface MusicControllerCallback {
        void onPlayingStateChanged(boolean isPlaying);
    }

    public void setMediaControllerCompatCallback(
            MusicControllerCallback musicControllerCallback) {
        mMusicControllerCallback = musicControllerCallback;
    }
}
