package com.cc.retrofitdemo.designmode.modes.proxy;

import com.cc.retrofitdemo.network.utils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SubjectInvocationHandler implements InvocationHandler {
    private static final String TAG = "SubjectInvocationHandle";
    //这个就是我们要代理的真实对象
    private Object subject;

    //构造方法，给我们要代理的真实对象赋初值
    public SubjectInvocationHandler(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        //在代理真实对象前我们可以添加一些自己的操作
        LogUtils.i(TAG, "before  Method=" + method.getName());
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(subject, args);
        //在代理真实对象后我们也可以添加一些自己的操作
        LogUtils.i(TAG, "after");
        return null;
    }
}
