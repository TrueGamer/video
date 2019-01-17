package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.BigBannerHolder;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class CustomBigBannerBean extends BaseCustomAdBean{

    private String imgurl;

    public CustomBigBannerBean(String downurl, String url, Long size, String packename, String appname,String imgurl) {
        super(downurl, url, size, packename, appname);
        this.imgurl=imgurl;
    }

    @Override
    public int getItemType() {
        return BigBannerHolder.class.hashCode();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
