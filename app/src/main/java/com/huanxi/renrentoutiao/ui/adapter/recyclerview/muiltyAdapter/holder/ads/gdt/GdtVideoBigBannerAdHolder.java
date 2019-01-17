package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.gdt;

import android.content.Context;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GdtBigBannerBean;
import com.qq.e.ads.nativ.NativeExpressADView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class GdtVideoBigBannerAdHolder extends BaseMuiltyViewHolder<GdtBigBannerBean> {

    @Override
    public void init(GdtBigBannerBean gdtBigBannerBean, BaseViewHolder helper, Context context) {

        //这里我们要做的一个逻辑就是；

        ViewGroup adContainer = (ViewGroup) helper.itemView;

        NativeExpressADView gdtadView = gdtBigBannerBean.getGdtadView();

        if(gdtadView != null){

            gdtadView.setPadding(0, 0, 0, UIUtil.dip2px(context, 10));


            if (adContainer.getChildCount() > 0
                    && adContainer.getChildAt(0) == gdtadView) {
                return;
            }

            if (adContainer.getChildCount() > 0) {
                adContainer.removeAllViews();
            }

            if (gdtadView.getParent() != null) {
                ((ViewGroup) gdtadView.getParent()).removeView(gdtadView);
            }

            adContainer.addView(gdtadView);
            gdtadView.render(); // 调用render方法后sdk才会开始展示广告
        }

    }
}
