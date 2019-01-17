package com.huanxi.renrentoutiao.net.bean;

/**
 * Created by Dinosa on 2018/2/8.
 */

public class ResWallettotalBean {

    /**
     * lastmoney : 100
     * totalmoney : 1000
     * yesterday : 200
     * rate : 0.23
     */

    private float lastmoney;
    private float totalmoney;
    private float yesterday;
    private float rate;

    public float getLastmoney() {
        return lastmoney;
    }

    public void setLastmoney(float lastmoney) {
        this.lastmoney = lastmoney;
    }

    public float getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(float totalmoney) {
        this.totalmoney = totalmoney;
    }

    public float getYesterday() {
        return yesterday;
    }

    public void setYesterday(float yesterday) {
        this.yesterday = yesterday;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
