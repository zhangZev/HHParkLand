package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/2/21.
 * 施工钱包实体
 */

public class SGWalletEntity extends IdEntity {
    /*
        expend:0,
        inputs:15,
        money:15,
        transactionTime:"2017-02-21 09:58",
    */
    @Expose
    private double expend;//支出

    @Expose
    private double inputs;//收入

    @Expose
    private String transactionTime;//交易时间

    @Expose
    private double money;//总金额

    public double getExpend() {
        return expend;
    }

    public void setExpend(double expend) {
        this.expend = expend;
    }

    public double getInputs() {
        return inputs;
    }

    public void setInputs(double inputs) {
        this.inputs = inputs;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
