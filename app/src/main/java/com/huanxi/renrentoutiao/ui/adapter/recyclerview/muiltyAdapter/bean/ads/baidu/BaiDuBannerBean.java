package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.baidu;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.baidu.BaiDuAdHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.gdt.GdtBigBannerAdHolder;
import com.qq.e.ads.nativ.NativeExpressADView;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class BaiDuBannerBean extends BaseMuiltyAdapterBean {

    //这里表示的是某一个广告的逻辑；
    private String code;
    private long adid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getAdid() {
        return adid;
    }

    public void setAdid(long adid) {
        this.adid = adid;
    }

    @Override
    public int getItemType() {
        return BaiDuAdHolder.class.hashCode();
    }
}
