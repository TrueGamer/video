package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomUpTitleDownImgBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class UpTitleDownImgHolder extends BaseCustomAdHolder<CustomUpTitleDownImgBean> {

    @Override
    public void init(final CustomUpTitleDownImgBean bean, BaseViewHolder helper, final Context context) {

        TextView mTvTitle = helper.getView(R.id.tv_title);
        ImageView mImageView = helper.getView(R.id.iv_image);

        mTvTitle.setText(bean.getContent());
        ImageUtils.loadImage(context,bean.getImgurl(),mImageView);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们执行一个跳转；
                if (bean.isDownloadAd()) {
                    //这里要做的逻辑就是判断开启任务下载
                    //这里要做的一个操作
                    startToDownload(context,bean);
                }else {
                    //这里表示要进入一个网页的操作；
                    startToGoWeb(context,bean.getUrl(),bean.getTitle());
                }
            }
        });

    }
}
