package com.cc.retrofitdemo.musicplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.retrofitdemo.R;
import com.cc.retrofitdemo.musicplayer.client.MusicController;
import com.cc.retrofitdemo.utils.LogUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener, MusicController.MusicControllerCallback {
    private ImageView mPlayNext;
    private ImageView mPlayPause;
    private ImageView mPlayPre;
    private TextView mSetPlayMedia;
    private static final String TAG = "MusicPlayerActivity";

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

    @Override
    public void onPlayingStateChanged(boolean isPlaying) {
        LogUtils.i(TAG, "" + isPlaying);
        if (isPlaying) {
            mPlayPause.setBackgroundResource(R.mipmap.icon_pause);
        } else {
            mPlayPause.setBackgroundResource(R.mipmap.icon_play);
        }
    }
}
