package com.huanxi.renrentoutiao.net.bean.user;

/**
 * Created by Dinosa on 2018/2/6.
 *
 * 拆红包的一个返回值（是否可以，有倒计时）
 */

public class ResGetRedPacketBean {


    /**
     * lasttime : 0
     */

    private int lasttime;

    private int integral;


    public int getLasttime() {
        return lasttime;
    }

    public void setLasttime(int lasttime) {
        this.lasttime = lasttime;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
