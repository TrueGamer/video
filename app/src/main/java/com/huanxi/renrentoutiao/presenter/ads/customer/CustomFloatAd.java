package com.huanxi.renrentoutiao.presenter.ads.customer;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.service.AdDownloadServiceNew;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.adapter.FloatAdBean;

/**
 * Created by Dinosa on 2018/3/28.
 * 这里是自定义浮标的业务逻辑了；
 */

public class CustomFloatAd {


    public void init(View helper, final FloatAdBean adBean, final Context context){

        final ImageView view = (ImageView) helper.findViewById(R.id.iv_float_icon);
       // ImageUtils.loadImage(context,adBean.getImgurl(),view);

        Glide.with(context)
                .load(adBean.getImgurl())
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        view.setImageDrawable(resource);
                    }
                });


        helper.setOnClickListener(new View.OnClickListener() {
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
