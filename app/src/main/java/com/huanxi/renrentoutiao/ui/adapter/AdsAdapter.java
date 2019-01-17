package com.huanxi.renrentoutiao.ui.adapter;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/10.
 */

public class AdsAdapter extends BaseAdsAdapter<AdBean> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AdsAdapter(List<AdBean> data) {
        super(data);
    }
    @Override
    protected void convert(BaseViewHolder helper, AdBean item) {
        super.convert(helper, item);
    }

}
