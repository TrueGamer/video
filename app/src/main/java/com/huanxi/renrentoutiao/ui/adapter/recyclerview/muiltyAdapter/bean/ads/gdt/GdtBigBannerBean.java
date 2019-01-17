package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.gdt.GdtBigBannerAdHolder;
import com.qq.e.ads.nativ.NativeExpressADView;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class GdtBigBannerBean extends BaseMuiltyAdapterBean {

    //这里表示的是某一个广告的逻辑；
    private NativeExpressADView gdtadView;
    private long adid;

    public GdtBigBannerBean(NativeExpressADView gdtadView) {
        this.gdtadView = gdtadView;
    }

    @Override
    public int getItemType() {
        return GdtBigBannerAdHolder.class.hashCode();
    }

    public NativeExpressADView getGdtadView() {
        return gdtadView;
    }

    public void setGdtadView(NativeExpressADView gdtadView) {
        this.gdtadView = gdtadView;
    }

    public long getAdid() {
        return adid;
    }

    public void setAdid(long adid) {
        this.adid = adid;
    }
}
