package com.cc.retrofitdemo.livedata;

import android.os.Bundle;
import android.widget.TextView;

import com.cc.retrofitdemo.R;
import com.cc.retrofitdemo.utils.LogUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

public class LiveDataActivity extends AppCompatActivity {
    private static final String TAG = "LiveDataActivity";
    private TextView mTextView, mMediatorLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livedata_layout);
        LogUtils.i(TAG, "onCreate");
        mTextView = findViewById(R.id.livedata);
        mMediatorLiveData = findViewById(R.id.mediatorLiveData);
        StockLiveData stockLiveData = new StockLiveData();
        //Transformations
        LiveData<String> liveData = Transformations.map(stockLiveData, input -> "LiveData.." + input);

        liveData.observe(this, s -> mTextView.setText(s));

        //MediatorLiveData
        MediatorLiveData<String> mediatorLiveData = new MediatorLiveData<String>();
        mediatorLiveData.addSource(liveData, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mediatorLiveData.postValue("mediatorLiveData " + s);
            }
        });
        mediatorLiveData.observe(this, s -> mMediatorLiveData.setText(s));

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }
}
