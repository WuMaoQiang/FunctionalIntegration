package com.cc.retrofitdemo.generic;

import java.util.Map;

public class PhoneBean {
    private int price;
    private int num;
    public Map<String, Integer> map;

    private int getNum() {
        return num;
    }

    private void setNum(int num) {
        this.num = num;
    }


    private void setPrice(int price) {
        this.price = price;
    }

    private int getPrice() {
        return price;
    }

    private void setPrice2(int price, int num) {
        this.price = price;
        this.num = num;
    }

    private int getTotal() {
        return price * num;
    }
}
