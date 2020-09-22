package com.cc.retrofitdemo.generic;

import android.os.Bundle;

import com.cc.retrofitdemo.R;
import com.cc.retrofitdemo.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class GenericActivity extends AppCompatActivity {
    private static final String TAG = "ReflectActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reflext_layout);

        attributeReflect();
        methodReflect();
        getGenericType();

    }

    /**
     * 私有变量
     */
    private void attributeReflect() {
        try {
            Field price = PhoneBean.class.getDeclaredField("price");
            Field num = PhoneBean.class.getDeclaredField("num");

            Object o = PhoneBean.class.newInstance();

            price.setAccessible(true);
            price.set(o, 2);
            num.setAccessible(true);
            num.set(o, 3);

            Method getTotal = Class.forName("com.cc.retrofitdemo.generic.PhoneBean").getDeclaredMethod("getTotal");
            getTotal.setAccessible(true);
            LogUtils.i(TAG, "attributeReflect==" + getTotal.invoke(o));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 私有方法
     */
    private void methodReflect() {

        try {
            Method setPrice = PhoneBean.class.getDeclaredMethod("setPrice2", int.class, int.class);
            Constructor<PhoneBean> constructor = PhoneBean.class.getConstructor();
            Object o = constructor.newInstance();
            setPrice.setAccessible(true);
            setPrice.invoke(o, 200, 5);

            Method getTotal = Class.forName("com.cc.retrofitdemo.generic.PhoneBean").getDeclaredMethod("getTotal");
            getTotal.setAccessible(true);
            LogUtils.i(TAG, "methodReflect==" + getTotal.invoke(o));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射获取获取泛型类型
     */
    public void getGenericType() {
        try {
            // 返回使用该Class对象创建的实例
            Field map = PhoneBean.class.getDeclaredField("map");
            Class<?> type = map.getType();
            LogUtils.i(TAG, "getType==" + type);
            ParameterizedType type2 = (ParameterizedType) map.getGenericType();
            LogUtils.i(TAG, "getGenericType==" + Arrays.toString(type2.getActualTypeArguments()));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
