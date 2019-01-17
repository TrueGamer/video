package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.ta;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaUpTitleDownImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom.BaseCustomAdHolder;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class TaUpTitleDownImgHolder extends BaseCustomAdHolder<TaUpTitleDownImgBean> {

    @Override
    public void init(TaUpTitleDownImgBean taUpTitleDownImgBean, BaseViewHolder helper, Context context) {


        //这里是推啊的广告的逻辑；
        //这里我们要做的一个逻辑就是；

        ViewGroup adContainer = (ViewGroup) helper.itemView;

        View taView =taUpTitleDownImgBean.getUpTitleDownImgView();

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
