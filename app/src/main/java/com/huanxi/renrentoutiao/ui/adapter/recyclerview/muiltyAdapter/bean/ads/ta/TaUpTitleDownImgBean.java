package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta;

import android.view.View;

import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.ta.TaUpTitleDownImgHolder;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class TaUpTitleDownImgBean extends BaseMuiltyAdapterBean {

    //这里要做的一个操作就是一样的操作；

    public View mUpTitleDownImgView;

    public TaUpTitleDownImgBean(View upTitleDownImgView) {
        mUpTitleDownImgView = upTitleDownImgView;
    }

    @Override
    public int getItemType() {
        return TaUpTitleDownImgHolder.class.hashCode();
    }

    public View getUpTitleDownImgView() {
        return mUpTitleDownImgView;
    }

    public void setUpTitleDownImgView(View upTitleDownImgView) {
        mUpTitleDownImgView = upTitleDownImgView;
    }
}
