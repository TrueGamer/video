package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta;

import android.view.View;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.ta.TaLeftTitleRightImgHolder;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class TaLeftTitleRightImgBean extends BaseMuiltyAdapterBean {

    public View mTaLeftImgView;
    public long adid;

    public TaLeftTitleRightImgBean(View taLeftImgView) {
        mTaLeftImgView = taLeftImgView;
    }

    @Override
    public int getItemType() {
        return TaLeftTitleRightImgHolder.class.hashCode();
    }

    public View getTaLeftImgView() {
        return mTaLeftImgView;
    }

    public void setTaLeftImgView(View taLeftImgView) {
        mTaLeftImgView = taLeftImgView;
    }

    public long getAdid() {
        return adid;
    }

    public void setAdid(long adid) {
        this.adid = adid;
    }
}
