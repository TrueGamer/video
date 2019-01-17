package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.UpTitleDownThreeImgHolder;

import java.util.List;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class CustomTitleDownThreeImgBean extends BaseCustomAdBean {

    //这里是上文下三图
    public List<String> imgUrls;
    public String title;    //网页内部的title;
    public String content; //这里是显示的内容；

    public CustomTitleDownThreeImgBean(String downurl, String url, Long size, String packename, String appname,String title,List<String> imgUrls,String content) {
        super(downurl, url, size, packename, appname);
        this.title=title;
        this.content=content;
        this.imgUrls=imgUrls;
    }

    @Override
    public int getItemType() {
        return UpTitleDownThreeImgHolder.class.hashCode();
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
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
}
