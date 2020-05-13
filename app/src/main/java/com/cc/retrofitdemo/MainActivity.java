package com.cc.retrofitdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.cc.retrofitdemo.designmode.BuilerDesignMode;
import com.cc.retrofitdemo.network.utils.LogUtils;
import com.cc.retrofitdemo.network.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        MainViewModel.getInstance().requestData();
        MainViewModel.getInstance().getData().observe(this, retrofitBean -> changeText(retrofitBean.toString()));
        builderDesignMode();

    }

    private void builderDesignMode() {
        BuilerDesignMode build = new BuilerDesignMode.Builder().buildData("11data").buildUrl("22url").build();
        LogUtils.i(TAG, build.getString());
    }

    private void changeText(String string) {
        mTv.setText(string);
    }
}
