package com.huanxi.renrentoutiao.net.bean.news;

/**
 * Created by Dinosa on 2018/2/5.
 *
 * 获取奖励的返回bean;
 */

public class ResAward {

    /**
     * integral : 10
     */

    private String integral;
    private String nextintegral;

    public String getNextintegral() {
        return nextintegral;
    }

    public void setNextintegral(String nextintegral) {
        this.nextintegral = nextintegral;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
