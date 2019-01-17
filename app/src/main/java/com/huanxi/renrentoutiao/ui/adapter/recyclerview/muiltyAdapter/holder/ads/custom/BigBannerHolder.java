package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomBigBannerBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class BigBannerHolder extends BaseCustomAdHolder<CustomBigBannerBean> {

    @Override
    public void init(final CustomBigBannerBean bean, BaseViewHolder view, final Context context) {

        //这里是20_3的一个广告的逻辑
        final ImageView bannerView = (ImageView)  view.getView(R.id.iv_ad_banner);
        ImageUtils.loadImage(context,bean.getImgurl(),bannerView);

        view.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bean.isDownloadAd()) {
                    //这里表示是下载的任务；
                    startToDownload(context,bean);

                }else {
                    //这里表示要进入一个网页的操作；
                    startToGoWeb(context,bean.getUrl(),"");
                }
            }
        });
    }





}
