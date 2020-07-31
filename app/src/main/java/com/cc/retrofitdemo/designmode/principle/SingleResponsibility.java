package com.cc.retrofitdemo.designmode.principle;

/**
 * description ：单一职责
 * 只用来处理跟订单相关的事务，如果想处理用户相关，不要写到这个类里。应该在写一个用户类，来处理用户相关事务。保证类的职责单一
 */
public class SingleResponsibility {
    private String mOrder;

    public String getOrder() {
        return mOrder;
    }

    public void setOrder(String order) {
        this.mOrder = order;
    }
}
