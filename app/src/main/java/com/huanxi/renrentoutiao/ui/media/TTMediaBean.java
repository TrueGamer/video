package com.huanxi.renrentoutiao.ui.media;

import java.util.List;

/**
 * 微头条列表适配器
 */
public class TTMediaBean {

    private String id;
    private String userIcon;
    private String userName;
    private String time;
    private String content;
    private String imgType; // 1 , 2 , 9
    private List<String> picUrl;
    private int zhuanFa;
    private int pinglun;
    private int zan;
    private String title;

    private String newUserId; // 头条号id
    private boolean dingyue;//是否订阅
    private boolean zanTy;//是否点赞

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }

    public int getZhuanFa() {
        return zhuanFa;
    }

    public void setZhuanFa(int zhuanFa) {
        this.zhuanFa = zhuanFa;
    }

    public int getPinglun() {
        return pinglun;
    }

    public void setPinglun(int pinglun) {
        this.pinglun = pinglun;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(String newUserId) {
        this.newUserId = newUserId;
    }

    public boolean isDingyue() {
        return dingyue;
    }

    public void setDingyue(boolean dingyue) {
        this.dingyue = dingyue;
    }

    public boolean isZanTy() {
        return zanTy;
    }

    public void setZanTy(boolean zanTy) {
        this.zanTy = zanTy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TTMediaBean{" +
                "id='" + id + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", imgType='" + imgType + '\'' +
                ", picUrl=" + picUrl +
                ", zhuanFa=" + zhuanFa +
                ", pinglun=" + pinglun +
                ", zan=" + zan +
                ", title='" + title + '\'' +
                ", newUserId='" + newUserId + '\'' +
                ", dingyue=" + dingyue +
                ", zanTy=" + zanTy +
                '}';
    }
}
