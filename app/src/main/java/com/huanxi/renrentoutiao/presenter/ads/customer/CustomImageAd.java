package com.huanxi.renrentoutiao.presenter.ads.customer;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.service.AdDownloadServiceNew;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/2/10.
 * 广告位；
 */

public class CustomImageAd {


    private  ImageView mBanner;

    public void init(BaseViewHolder helper, final AdBean adBean, final Context context){

        ImageView view = (ImageView) helper.getView(R.id.iv_ad_banner);
        ImageUtils.loadImage(context,adBean.getImgurl(),view);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(adBean.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    Intent intent = new Intent(context, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,adBean);
                    context.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    context.startActivity(WebHelperActivity.getIntent(context,adBean.getUrl(),adBean.getTitle(),false));
                }
            }
        });
    }

}
