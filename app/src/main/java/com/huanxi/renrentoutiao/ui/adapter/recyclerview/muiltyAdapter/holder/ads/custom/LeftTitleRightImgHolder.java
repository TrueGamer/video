package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomLeftTitlRightImgBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/4/10.
 * 左图右文的holder对象；
 */

public class LeftTitleRightImgHolder extends BaseCustomAdHolder<CustomLeftTitlRightImgBean> {
    @Override
    public void init(final CustomLeftTitlRightImgBean bean, BaseViewHolder helper, final Context context) {


        TextView textView = (TextView) helper.getView(R.id.tv_news_title);

        textView.setText(bean.getContent());

        ImageView imageView = (ImageView) helper.getView(R.id.iv_news_img);
        ImageUtils.loadImage(context,bean.getImgurl(),imageView);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里要做的逻辑就是
                if (bean.isDownloadAd()) {
                    //这里要做的逻辑就是判断开启任务下载
                    startToDownload(context,bean);
                }else {
                    //这里表示要进入一个网页的操作；
                    startToGoWeb(context,bean.getUrl(),bean.getTitle());
                }
            }
        });

    }
}
