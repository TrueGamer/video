package com.huanxi.renrentoutiao.ui.media;

import java.io.Serializable;
import java.util.List;

/**
 * 媒体头条号用户信息
 */
public class MediaUserBean implements Serializable{

    /**
     * jianjie: "新华网是国家通讯社新华社主办的综合新闻信息服务门户网站，是中国最具影响力的网络媒体和具有全球影响力的中文网站。",
     newUserId: 1,
     newUserName: "新华网",
     newUserUrl: "//p3.pstatp.com/thumb/3658/7378365093",
     subscription: 0
     */
    private String jiangjie;
    private int newUserId;
    private String newUserName;
    private String newUserUrl;
    private String subscription;
    private String userId;// 当前登陆人得id

    private List<TTMediaBean> newsList;

    public String getJiangjie() {
        return jiangjie;
    }

    public void setJiangjie(String jiangjie) {
        this.jiangjie = jiangjie;
    }

    public int getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(int newUserId) {
        this.newUserId = newUserId;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getNewUserUrl() {
        return newUserUrl;
    }

    public void setNewUserUrl(String newUserUrl) {
        this.newUserUrl = newUserUrl;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<TTMediaBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<TTMediaBean> newsList) {
        this.newsList = newsList;
    }
}
