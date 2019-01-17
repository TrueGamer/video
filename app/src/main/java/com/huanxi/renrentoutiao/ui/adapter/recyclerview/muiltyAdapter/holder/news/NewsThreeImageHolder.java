package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.db.ta.sdk.TmActivity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.news.NewsDetailActivity2;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.news.NewsThreeImageBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class NewsThreeImageHolder extends BaseMuiltyViewHolder<NewsThreeImageBean>{


    @Override
    public void init(final NewsThreeImageBean newsThreeImageBean, BaseViewHolder holder, final Context context) {

        holder.setText(R.id.tv_news_title, newsThreeImageBean.getTopic());

        ImageUtils.loadImage(context, newsThreeImageBean.getImageUrls().get(0), (ImageView) holder.getView(R.id.iv_img1));
        ImageUtils.loadImage(context,  newsThreeImageBean.getImageUrls().get(1), (ImageView) holder.getView(R.id.iv_img2));
        ImageUtils.loadImage(context,  newsThreeImageBean.getImageUrls().get(2), (ImageView) holder.getView(R.id.iv_img3));

        holder.setText(R.id.tv_source, newsThreeImageBean.getSource());
        holder.setText(R.id.tv_time, newsThreeImageBean.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsThreeImageBean.isWebContent()) {
                    //这里跳转到东方头条
                    TmActivity.a(context, newsThreeImageBean.getUrl());
                }else{
                    //跳转新闻详情；
                    context.startActivity(NewsDetailActivity2.getIntent(context,newsThreeImageBean.getUrl()
                            ,newsThreeImageBean.getUrlMd5() , newsThreeImageBean.getNewsType() , newsThreeImageBean.getPageNum()));
                }
            }
        });

    }
}
