package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomTitleDownThreeImgBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/4/10.
 * 上文下三图的一个holder对象；
 */

public class UpTitleDownThreeImgHolder extends BaseCustomAdHolder<CustomTitleDownThreeImgBean> {


    @Override
    public void init(final CustomTitleDownThreeImgBean bean, BaseViewHolder helper, final Context context) {


        TextView view =  helper.getView(R.id.tv_ad_title);

        view.setText(bean.getContent());

        ImageView image1 = (ImageView) helper.getView(R.id.iv_img1);
        ImageView image2 = (ImageView) helper.getView(R.id.iv_img2);
        ImageView image3 = (ImageView) helper.getView(R.id.iv_img3);

        try {
            ImageUtils.loadImage(context,bean.getImgUrls().get(0),image1);
            ImageUtils.loadImage(context,bean.getImgUrls().get(1),image2);
            ImageUtils.loadImage(context,bean.getImgUrls().get(2),image3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
