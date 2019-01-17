package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom;

import android.text.TextUtils;

import com.huanxi.renrentoutiao.service.DownloadServiceInterface;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;

/**
 * Created by Dinosa on 2018/4/10.
 */

public abstract class BaseCustomAdBean extends BaseMuiltyAdapterBean implements DownloadServiceInterface{

    //这里是点击广告之后的一个操作的逻辑
    private String url;
    private String downurl;
    private Long size;
    private String packename;
    private String appname;




    public BaseCustomAdBean( String downurl, String url,Long size, String packename, String appname) {
        this.url = url;
        this.downurl = downurl;
        this.size = size;
        this.packename = packename;
        this.appname = appname;
    }

    //==========

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPackename() {
        return packename;
    }

    public void setPackename(String packename) {
        this.packename = packename;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }


    /**
     * 这里要做的一个操作就是是否是下载的广告的逻辑
     * @return
     */
    public boolean isDownloadAd(){
        return !TextUtils.isEmpty(downurl);
    }


    //==这里是判断下载的逻辑====
    @Override
    public String getPackageName() {
        return appname;
    }

    @Override
    public String getAppName() {
        return appname;
    }

    @Override
    public Long getAppSize() {
        return size;
    }

    @Override
    public String getDownloadUrl() {
        return downurl;
    }
}
