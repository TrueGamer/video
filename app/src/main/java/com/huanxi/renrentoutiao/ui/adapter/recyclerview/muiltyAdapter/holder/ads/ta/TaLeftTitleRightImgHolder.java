package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.ta;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaLeftTitleRightImgBean;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class TaLeftTitleRightImgHolder extends BaseMuiltyViewHolder<TaLeftTitleRightImgBean> {

    @Override
    public void init(TaLeftTitleRightImgBean taLeftTitleRightImgBean, BaseViewHolder helper, Context context) {

        //这里是推啊的广告的逻辑；
        //这里我们要做的一个逻辑就是；

        ViewGroup adContainer = (ViewGroup) helper.itemView;

        View taView = taLeftTitleRightImgBean.getTaLeftImgView();

        if(taView != null){

            if (adContainer.getChildCount() > 0
                    && adContainer.getChildAt(0) == taView) {
                return;
            }

            if (adContainer.getChildCount() > 0) {
                adContainer.removeAllViews();
            }

            if (taView.getParent() != null) {
                ((ViewGroup) taView.getParent()).removeView(taView);
            }

            adContainer.addView(taView);
        }

    }

}
