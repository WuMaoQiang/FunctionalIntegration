package com.cc.retrofitdemo.musicplayer.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v4.media.session.PlaybackStateCompat;

import com.cc.retrofitdemo.utils.LogUtils;

import java.io.IOException;

public class MediaPlayerImpl implements IPlayController, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private static final String TAG = "MediaPlayerImpl";
    private int mPlayerState;
    private Callback mCallback;
    private String currentMusic;
    private boolean mSeekToEnd;
    private long currentPosition;

    public MediaPlayerImpl(Context context) {
        mContext = context;
        if (null == mMediaPlayer) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnPreparedListener(this);
        }

    }

    @Override
    public void setPlayableMedia() {
        currentPosition = 0;
        mMediaPlayer.reset();
        currentMusic = "菊花臺";
        try {
            AssetFileDescriptor fd = mContext.getAssets().openFd("菊花台.mp3");
            mMediaPlayer.setDataSource(fd.getFileDescriptor(),
                    fd.getStartOffset(), fd.getLength());
            mMediaPlayer.prepare();
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPlayableMedia() {
        return currentMusic;
    }

    @Override
    public void start() {
        if (currentPosition == 0) {
            mMediaPlayer.start();
        } else {
            seekTo((int) currentPosition);
            mMediaPlayer.start();
        }
        mPlayerState = PlaybackStateCompat.STATE_PLAYING;
        onPlayStatusChange();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        LogUtils.i(TAG, "getCurrentPosition=" + mMediaPlayer.getCurrentPosition());
        if (mMediaPlayer != null) {
            if (mSeekToEnd) {
                return getDuration();
            }
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {
        currentPosition = getCurrentPosition();
        mMediaPlayer.pause();
        mPlayerState = PlaybackStateCompat.STATE_PAUSED;
        onPlayStatusChange();
    }

    @Override
    public void seekTo(long position) {
        if (mMediaPlayer != null) {
            long duration = getDuration();
            if (duration == position) {
                mSeekToEnd = true;
            }
            LogUtils.i(TAG, "seekTo  position" + position);
            mMediaPlayer.seekTo((int) position);
        }
    }

    @Override
    public long getDuration() {
        if (null != mMediaPlayer) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public void skipToNext() {
        this.setPlayableMedia();
    }

    @Override
    public void skipToPrevious() {
        this.setPlayableMedia();
    }

    @Override
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    private void onPlayStatusChange() {
        LogUtils.i(TAG, "onPlayStatusChange" + mCallback);

        if (mCallback != null) {
            mCallback.onPlaybackStatusChanged(mPlayerState);
        }
    }
}
