package com.cc.retrofitdemo.designmode;

import android.os.Bundle;

import com.cc.retrofitdemo.R;
import com.cc.retrofitdemo.designmode.modes.BuilerDesignMode;
import com.cc.retrofitdemo.designmode.modes.proxy.SubjectInvocationHandler;
import com.cc.retrofitdemo.designmode.modes.proxy.RealSubject;
import com.cc.retrofitdemo.designmode.modes.SingletonDesignMode;
import com.cc.retrofitdemo.designmode.modes.proxy.Subject;
import com.cc.retrofitdemo.utils.LogUtils;

import java.lang.reflect.Proxy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DesignModeActivity extends AppCompatActivity {
    private static final String TAG = "DesignModeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_mode);
        singletonDesignMode();
        builderDesignMode();
        dynamicProxyMode();
    }

    /**
     * 单例模式
     */
    private void singletonDesignMode() {
        SingletonDesignMode instance = SingletonDesignMode.getInstance();
    }

    /**
     * 建造者模式
     */
    private void builderDesignMode() {
        BuilerDesignMode build = new BuilerDesignMode.Builder().buildData("11data").buildUrl("22url").build();
        LogUtils.i(TAG, build.getString());
    }

    /**
     * 动态代理
     */
    private void dynamicProxyMode() {
        //被代理类
        Subject realSubject = new RealSubject();
        //我们要代理哪个类，就将该对象传进去，最后是通过该被代理对象来调用其方法的
        SubjectInvocationHandler subjectInvocationHandler = new SubjectInvocationHandler(realSubject);

        Subject subject = (Subject) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[]{Subject.class}, subjectInvocationHandler);

        subject.sayHello("sss");
        subject.sayGoodBye();

    }
}
