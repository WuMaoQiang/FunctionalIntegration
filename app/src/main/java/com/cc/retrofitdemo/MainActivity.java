package com.cc.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cc.retrofitdemo.designmode.DesignModeActivity;
import com.cc.retrofitdemo.musicplayer.MusicPlayerActivity;
import com.cc.retrofitdemo.navigation.NavigationActivity;
import com.cc.retrofitdemo.navigation.fragments.FragmentMain;
import com.cc.retrofitdemo.network.bean.RemoteDataResource;
import com.cc.retrofitdemo.network.viewmodel.MainViewModel;
import com.cc.retrofitdemo.threadpool.ExceptionThread;
import com.cc.retrofitdemo.threadpool.ThreadPoolManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTv;
    private TextView mDesginMode;
    private TextView mExceptionThread;
    private TextView mNavigation;
    private TextView mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

//        MainViewModel.getInstance().requestData();
//        MainViewModel.getInstance().requestArticleList("0");
//        MainViewModel.getInstance().doSearch("0", "单例模式");
        MainViewModel.getInstance().userLogin("", "");
//        MainViewModel.getInstance().map();
        MainViewModel.getInstance().getData().observe(this, MainActivity.this::changeText);
    }

    private void initView() {
        mTv = findViewById(R.id.tv);
        mDesginMode = findViewById(R.id.desgin_mode);
        mDesginMode.setOnClickListener(this);
        mExceptionThread = findViewById(R.id.exception_thread);
        mExceptionThread.setOnClickListener(this);
        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnClickListener(this);
        mPlayer = findViewById(R.id.player);
        mPlayer.setOnClickListener(this);

    }

    private void changeText(RemoteDataResource remoteDataResource) {
        mTv.setText(remoteDataResource.toString());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.desgin_mode://设计模式
                startActivity(new Intent(MainActivity.this, DesignModeActivity.class));
                break;
            case R.id.exception_thread:
                ThreadPoolManager.getInstance().doSubmit(new ExceptionThread());
                break;
            case R.id.navigation:
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));
                FragmentMain.newInstance("q", "q");
                break;
            case R.id.player:
                startActivity(new Intent(MainActivity.this, MusicPlayerActivity.class));
                break;
            default:
        }
    }
}
