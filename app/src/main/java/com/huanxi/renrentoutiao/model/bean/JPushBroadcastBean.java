package com.huanxi.renrentoutiao.model.bean;

import java.io.Serializable;

/**
 * Created by Dinosa on 2018/4/13.
 *
 * 这里是推送传递过来的数据的操作；
 */

public class JPushBroadcastBean implements Serializable{


    /**
     * title : 女孩去相亲，没想到碰上奇葩暴发户，看一次笑一次！
     * type : 2
     * urlmd5 :  a2700e3904a0fe18f1fdd6b55e9e9084
     * video_id : 7992e4a5ec8d45c8b0f005db425a8c78
     */

    private String title;
    private String type;
    private String urlmd5;
    private String video_id;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlmd5() {
        return urlmd5;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
