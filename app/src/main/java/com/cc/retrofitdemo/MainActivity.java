package com.cc.retrofitdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.cc.retrofitdemo.network.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        MainViewModel.getInstance().requestData();
        MainViewModel.getInstance().getData().observe(this, retrofitBean -> changeText(retrofitBean.toString()));
    }

    private void changeText(String string) {
        mTv.setText(string);
    }
}
