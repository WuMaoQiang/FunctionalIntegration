package com.cc.retrofitdemo.designmode;

import android.os.Bundle;

import com.cc.retrofitdemo.R;
import com.cc.retrofitdemo.designmode.modes.BuilerDesignMode;
import com.cc.retrofitdemo.designmode.modes.SingletonDesignMode;
import com.cc.retrofitdemo.network.utils.LogUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DesignModeActivity extends AppCompatActivity {
    private static final String TAG = "DesignModeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_mode);
        builderDesignMode();
        singletonDesignMode();
    }

    private void singletonDesignMode() {
        SingletonDesignMode instance = SingletonDesignMode.getInstance();
    }

    private void builderDesignMode() {
        BuilerDesignMode build = new BuilerDesignMode.Builder().buildData("11data").buildUrl("22url").build();
        LogUtils.i(TAG, build.getString());
    }
}
