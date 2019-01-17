package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.UpTitleDownImgHolder;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class CustomUpTitleDownImgBean extends BaseCustomAdBean {

    String title;
    String content;
    String imgurl;

    public CustomUpTitleDownImgBean(String downurl, String url, Long size, String packename, String appname, String title, String content, String imgurl) {
        super(downurl, url, size, packename, appname);
        this.title = title;
        this.content = content;
        this.imgurl = imgurl;
    }

    @Override
    public int getItemType() {
        return UpTitleDownImgHolder.class.hashCode();
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
}
