package com.huanxi.renrentoutiao.net.bean;

/**
 * Created by Dinosa on 2018/3/1.
 */

public class ResReadAwarad {

    /**
     * integral : 10
     * extra : 10
     * lastcount : 16/20
     */

    private int integral;
    private int extra;
    private String lastcount;
    private String lastcount_new;

    public String getLastcount_new() {
        return lastcount_new;
    }

    public void setLastcount_new(String lastcount_new) {
        this.lastcount_new = lastcount_new;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public String getLastcount() {
        return lastcount;
    }

    public void setLastcount(String lastcount) {
        this.lastcount = lastcount;
    }

    @Override
    public String toString() {
        return "ResReadAwarad{" +
                "integral=" + integral +
                ", extra=" + extra +
                ", lastcount='" + lastcount + '\'' +
                ", lastcount_new='" + lastcount_new + '\'' +
                '}';
    }
}
