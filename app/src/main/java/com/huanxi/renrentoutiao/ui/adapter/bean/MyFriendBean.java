package com.huanxi.renrentoutiao.ui.adapter.bean;

/**
 * Created by Dinosa on 2018/2/24.
 */

public class MyFriendBean {


    /**
     * nickname : 123123
     * addtime : 2018-02-24 11:10:48
     * price : 10
     */

    private String nickname;
    private String addtime;
    private String price;
    private String totalPrice;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
