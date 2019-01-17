package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.news;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.news.NewsOneImageHolder;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class NewsOneImageBean extends BaseMuiltyAdapterBean{

    public String imageUrl;
    public String topic;
    public String source;
    public String date;


    public String url;
    public String urlMd5;

    public boolean isWebContent;

    public String new_type;
    public int pageNum;



    public NewsOneImageBean(String imageUrl, String topic, String source, String date, String url,
                            String urlMd5,boolean isWebContent , String newsType , int pageNum) {
        this.imageUrl = imageUrl;
        this.topic = topic;
        this.source = source;
        this.date = date;
        this.url = url;
        this.urlMd5 = urlMd5;
        this.isWebContent=isWebContent;
        this.new_type = newsType;
        this.pageNum = pageNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }

    public boolean isWebContent(){
        return isWebContent;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNew_type() {
        return new_type;
    }

    public void setNew_type(String new_type) {
        this.new_type = new_type;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public int getItemType() {
        return NewsOneImageHolder.class.hashCode();
    }
}
