package com.huanxi.renrentoutiao.model.bean.news;

/**
 * Created by Dinosa on 2018/3/28.
 * 这里是网页的广告；
 */

public class NewsCustomWebAd {

    private int id;
    private String title;
    private String content;
    private String imgurl;

    private String url;

    private String type;
    private String code;
    private String qmttcontenttype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQmttcontenttype() {
        return qmttcontenttype;
    }

    public void setQmttcontenttype(String qmttcontenttype) {
        this.qmttcontenttype = qmttcontenttype;
    }
}
