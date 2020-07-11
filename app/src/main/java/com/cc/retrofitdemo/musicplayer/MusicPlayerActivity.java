package com.cc.retrofitdemo.musicplayer;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cc.retrofitdemo.R;
import com.cc.retrofitdemo.musicplayer.client.MusicController;
import com.cc.retrofitdemo.musicplayer.client.MusicProgressLiveData;
import com.cc.retrofitdemo.utils.LogUtils;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener, MusicController.MusicControllerCallback {
    private static final String TAG = "MusicPlayerActivity";

    private ImageView mPlayNext;
    private ImageView mPlayPause;
    private ImageView mPlayPre;
    private TextView mSetPlayMedia, mCurrentMusicPosition, mMusicDuration;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);
        initView();
    }

    private void initView() {
        mPlayNext = (ImageView) findViewById(R.id.play_next);
        mPlayPause = (ImageView) findViewById(R.id.play_pause);
        mPlayPre = (ImageView) findViewById(R.id.play_pre);
        mSetPlayMedia = findViewById(R.id.set_play_media);
        mCurrentMusicPosition = findViewById(R.id.current_music_position);
        mMusicDuration = findViewById(R.id.music_duration);
        mSeekBar = findViewById(R.id.seekBar);
        mPlayNext.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mPlayPre.setOnClickListener(this);
        mSetPlayMedia.setOnClickListener(this);
        if (MusicController.getInstance().isPlaying()) {
            mPlayPause.setBackgroundResource(R.mipmap.icon_pause);
        } else {
            mPlayPause.setBackgroundResource(R.mipmap.icon_play);
        }
        MusicController.getInstance().setMediaControllerCompatCallback(this);
        MusicProgressLiveData.getInstance().observe(this, update -> onProgressUpdate());

    }

    private void onProgressUpdate() {
        LogUtils.i(TAG, "onProgressUpdate" + MusicController.getInstance().getCurrentPosition());
        mCurrentMusicPosition.setText("" + formatTime(MusicController.getInstance().getCurrentPosition()));
        mSeekBar.setMax((123456));
        mSeekBar.setProgress(MusicController.getInstance().getCurrentPosition());
        mMusicDuration.setText("3:00");//需要从Media对象中获取播放时长
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause:
                if (MusicController.getInstance().isPlaying()) {
                    MusicController.getInstance().pause();
                } else {
                    MusicController.getInstance().play();
                }
                break;
            case R.id.play_pre:
                MusicController.getInstance().playPrev();
                break;
            case R.id.play_next:
                MusicController.getInstance().playNext();
                break;
            case R.id.set_play_media:
                if (!MusicController.getInstance().isPlaying()) {
                    MusicController.getInstance().play();
                }
                break;
            default:
        }
    }

    public String formatTime(long timeMillis) {
        int m = (int) (timeMillis / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((timeMillis / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return mm + ":" + ss;
    }

    @Override
    public void onPlayingStateChanged(boolean isPlaying) {
        LogUtils.i(TAG, "" + isPlaying);
        if (isPlaying) {
            mPlayPause.setBackgroundResource(R.mipmap.icon_pause);
            MusicProgressLiveData.getInstance().sendUpdateAction();
        } else {
            mPlayPause.setBackgroundResource(R.mipmap.icon_play);
            MusicProgressLiveData.getInstance().cancelUpdateAction();

        }
    }
}
