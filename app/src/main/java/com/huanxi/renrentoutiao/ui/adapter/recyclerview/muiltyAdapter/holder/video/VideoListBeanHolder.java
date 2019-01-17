package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.video;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.utils.FormatUtils;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class VideoListBeanHolder extends BaseMuiltyViewHolder<VideoListBean> {

    @Override
    public void init(final VideoListBean videoBean, BaseViewHolder helper, final Context context) {
        //这里初始化视频的条目；
        try {
            helper.setText(R.id.tv_news_title, videoBean.getTitle());

            helper.setText(R.id.tv_duration, FormatUtils.formatSecondToTime(videoBean.getVideo_duration()));

            helper.setText(R.id.tv_source,videoBean.getSource());
            helper.getView(R.id.tv_time).setVisibility(View.INVISIBLE);

            Glide.with(context).load(videoBean.getImgUrl()).into(((ImageView) helper.getView(R.id.iv_image)));

            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(VideoItemDetailActivity.getIntent(context, videoBean));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
