package com.huanxi.renrentoutiao.net.bean;

/**
 * Created by Dinosa on 2018/2/1.
 */

public class ResCheckVersion {


    /**
     * versionCode : 1.1
     * versionName : 1.1
     * url : http://........
     */

    private String versionCode;
    private String versionName;
    private String url;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
