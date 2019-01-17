package com.huanxi.renrentoutiao.presenter.ads.customer;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.service.AdDownloadServiceNew;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/2/10.
 * 图片和标题；
 */

public class TitleAndImgAd {

    private  TextView mTvTitle;
    private  ImageView mImageView;


    public void init(BaseViewHolder viewHolder, final AdBean bean, final Context context){

        mTvTitle = viewHolder.getView(R.id.tv_title);
        mImageView = viewHolder.getView(R.id.iv_image);

        mTvTitle.setText(bean.getCont());
        ImageUtils.loadImage(context,bean.getUrl(),mImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们执行一个跳转；
                if (!TextUtils.isEmpty(bean.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    //这里要做的一个操作
                    Intent intent = new Intent(context, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,bean);
                    context.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    context.startActivity(WebHelperActivity.getIntent(context,bean.getUrl(),bean.getTitle(),false));
                }
            }
        });
    }
}
