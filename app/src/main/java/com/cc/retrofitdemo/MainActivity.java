package com.cc.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cc.retrofitdemo.designmode.DesignModeActivity;
import com.cc.retrofitdemo.network.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTv;
    private TextView mDesginMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

//        MainViewModel.getInstance().getData();
//        MainViewModel.getInstance().requestArticleList("0");
        MainViewModel.getInstance().doSearch("0", "单例模式");
//        MainViewModel.getInstance().userLogin("", "");
        MainViewModel.getInstance().getData().observe(this, remoteDataResource -> MainActivity.this.changeText(remoteDataResource.toString()));
    }

    private void initView() {
        mTv = findViewById(R.id.tv);
        mDesginMode = findViewById(R.id.desgin_mode);
        mDesginMode.setOnClickListener(this);
    }

    private void changeText(String string) {
        mTv.setText(string);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.desgin_mode://设计模式
                startActivity(new Intent(MainActivity.this, DesignModeActivity.class));
                break;
            case 0:
                break;
            default:
        }
    }
}
