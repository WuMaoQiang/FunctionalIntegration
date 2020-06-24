package com.cc.retrofitdemo.musicplayer.player;


public interface IPlayController {

    void setPlayableMedia();

    String getPlayableMedia();

    void start();

    boolean isPlaying();

    long getCurrentPosition();

    void stop();

    void pause();

    void seekTo(long var1);

    long getDuration();

    void skipToNext();

    void skipToPrevious();


    void setCallback(IPlayController.Callback var1);

    public interface Callback {
        void onCompletion(boolean var1);

        void onPlaybackStatusChanged(int var1);

        void onError(int var1, String var2);

        void onProcessUpdate(long var1, long var3);
    }
}
