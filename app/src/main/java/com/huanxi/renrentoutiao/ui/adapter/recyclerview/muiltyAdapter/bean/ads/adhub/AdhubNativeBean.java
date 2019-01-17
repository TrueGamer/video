package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.adhub;

import android.view.View;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.adhub.AdhubNativeHolder;
import com.hubcloud.adhubsdk.NativeAd;
import com.hubcloud.adhubsdk.NativeAdResponse;

import java.util.List;

/**
 * Created by gdhuo on 2018/12/31.
 */
public class AdhubNativeBean extends BaseMuiltyAdapterBean {

    private NativeAdResponse response;
    private NativeAd nativeAd;

    public NativeAdResponse getResponse() {
        return response;
    }

    public void setResponse(NativeAdResponse response) {
        this.response = response;
    }

    public NativeAd getNativeAd() {
        return nativeAd;
    }

    public void setNativeAd(NativeAd nativeAd) {
        this.nativeAd = nativeAd;
    }

    @Override
    public int getItemType() {
        return AdhubNativeHolder.class.hashCode();//注意 此处有坑  是Holder的hash值！！！
    }
}
