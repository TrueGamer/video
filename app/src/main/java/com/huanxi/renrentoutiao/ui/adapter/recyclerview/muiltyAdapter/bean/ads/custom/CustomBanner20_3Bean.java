package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.Banner20_3Holder;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class CustomBanner20_3Bean extends BaseCustomAdBean{

    private String imgurl;

    public CustomBanner20_3Bean(String downurl,String url,  Long size, String packename, String appname,String imgurl) {
        super(url, downurl, size, packename, appname);
        this.imgurl=imgurl;
    }

    @Override
    public int getItemType() {
        return Banner20_3Holder.class.hashCode();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
