package com.cc.retrofitdemo.designmode.principle;

import com.cc.retrofitdemo.utils.LogUtils;

/**
 * description ：接口隔离
 * A类使用接口I依赖B类。C类使用接口I依赖D类.
 * A类只使用了方法1、2、3
 * C类只使用了方法1、4、5
 * 但是B、D都实现了接口的全部方法
 */
public class InterfaceSegregation {
    private static final String TAG = "InterfaceSegregation";

//    public static void main(String[] args) {
//        A a = new A();
//        a.function1(new B());
//        a.function2(new B());
//        a.function3(new B());
//        C c = new C();
//        c.function1(new D());
//        c.function4(new D());
//        c.function5(new D());
//    }


    interface IInterface1 {
        void function1();
    }

    interface IInterface2 {
        void function2();

        void function3();
    }

    interface IInterface3 {
        void function4();

        void function5();
    }



    class A {
        public void function1(IInterface1 iInterface1) {
            iInterface1.function1();
        }

        public void function2(IInterface2 iInterface2) {
            iInterface2.function2();
        }

        public void function3(IInterface2 iInterface2) {
            iInterface2.function3();
        }
    }

    class C {
        public void function1(IInterface1 iInterface1) {
            iInterface1.function1();
        }

        public void function4(IInterface3 iInterface3) {
            iInterface3.function4();
        }

        public void function5(IInterface3 iInterface3) {
            iInterface3.function5();
        }
    }


    class B implements IInterface1, IInterface2 {
        private static final String TAG = "B";

        @Override
        public void function1() {
            LogUtils.i(TAG, "B function1");
        }

        @Override
        public void function2() {
            LogUtils.i(TAG, "B function2");
        }

        @Override
        public void function3() {
            LogUtils.i(TAG, "B function3");
        }
    }

    class D implements IInterface1, IInterface3 {
        private static final String TAG = "D";

        @Override
        public void function1() {
            LogUtils.i(TAG, "D function1");
        }

        @Override
        public void function4() {
            LogUtils.i(TAG, "D function4");
        }

        @Override
        public void function5() {
            LogUtils.i(TAG, "D function5");
        }
    }
}
