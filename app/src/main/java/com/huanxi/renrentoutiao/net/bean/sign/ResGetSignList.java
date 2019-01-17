package com.huanxi.renrentoutiao.net.bean.sign;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/6.
 */

public class ResGetSignList {

    /**
     * hassign : 1  //是否被签到；
     * hasday : 4   //已经连续签到的天数；
     */

    private int hassign;
    private int hasday;

    private List<String> integrallist;

    private String integral;

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public int getHassign() {
        return hassign;
    }

    public void setHassign(int hassign) {
        this.hassign = hassign;
    }

    public int getHasday() {
        return hasday;
    }

    public void setHasday(int hasday) {
        this.hasday = hasday;
    }


    public List<String> getIntegrallist() {
        return integrallist;
    }

    public void setIntegrallist(List<String> integrallist) {
        this.integrallist = integrallist;
    }
}
