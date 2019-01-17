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
 * Created by Dinosa on 2018/3/28.
 */

public class CustomLeftTitleRightImgAd {


    private TextView mTvTitle;
    private ImageView mImageView;


    public void init(BaseViewHolder helper, final AdBean item, final Context mActivity){

        TextView textView = (TextView) helper.getView(R.id.tv_news_title);

        textView.setText(item.getCont());

        ImageView imageView = (ImageView) helper.getView(R.id.iv_news_img);
        ImageUtils.loadImage(mActivity,item.getImgurl(),imageView);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里要做的逻辑就是
                if (!TextUtils.isEmpty(item.getDownurl())) {
                    //这里要做的逻辑就是判断开启任务下载
                    Intent intent = new Intent(mActivity, AdDownloadServiceNew.class);
                    intent.putExtra(AdDownloadServiceNew.DOWNLOAD_INTERFACE_BEAN,item);
                    mActivity.startService(intent);
                }else {
                    //这里表示要进入一个网页的操作；
                    mActivity.startActivity(WebHelperActivity.getIntent(mActivity,item.getUrl(),item.getTitle(),false));
                }
            }
        });
    }

}
