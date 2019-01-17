package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomBanner20_3Bean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/4/10.
 * 这里是20比3的自定义的广告逻辑
 */

public class Banner20_3Holder extends BaseCustomAdHolder<CustomBanner20_3Bean> {

    @Override
    public void init(final CustomBanner20_3Bean bean, BaseViewHolder view, final Context context) {

        //这里是20_3的一个广告的逻辑
        final ImageView bannerView = (ImageView)  view.getView(R.id.iv_custom_banner);
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
