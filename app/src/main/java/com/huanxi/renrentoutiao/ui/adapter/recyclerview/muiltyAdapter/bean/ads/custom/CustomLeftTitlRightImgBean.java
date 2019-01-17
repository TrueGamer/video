package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.LeftTitleRightImgHolder;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class CustomLeftTitlRightImgBean extends BaseCustomAdBean{

    String content;
    String title;
    String imgurl;

    public CustomLeftTitlRightImgBean(String downurl, String url, Long size, String packename, String appname, String content, String title, String imgurl) {
        super(downurl, url, size, packename, appname);
        this.content = content;
        this.title = title;
        this.imgurl = imgurl;
    }

    @Override
    public int getItemType() {
        return LeftTitleRightImgHolder.class.hashCode();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
