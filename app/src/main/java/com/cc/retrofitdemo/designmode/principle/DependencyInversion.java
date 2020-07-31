package com.cc.retrofitdemo.designmode.principle;

import com.cc.retrofitdemo.utils.LogUtils;

/**
 * description ：依赖倒置
 * 想实现一个接受消息的功能
 */
public class DependencyInversion {

    DependencyInversion() {
        new Person().receive(new Email());
        new Person().receive(new WxChat());
    }


    static class Person {
        private static final String TAG = "Person";

        void receive(IReceiver receiver) {
            String info = receiver.getInfo();
            LogUtils.i(TAG, info);
        }
    }

    interface IReceiver {
        String getInfo();
    }

    static class Email implements IReceiver {

        @Override
        public String getInfo() {
            return "电子邮件";
        }
    }

    static class WxChat implements IReceiver {

        @Override
        public String getInfo() {
            return "微信";
        }
    }
}
