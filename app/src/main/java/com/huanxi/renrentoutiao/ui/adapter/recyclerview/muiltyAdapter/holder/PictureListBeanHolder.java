package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.other.PictureDetailActivity;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.PictureBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.utils.FormatUtils;

import java.util.ArrayList;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class PictureListBeanHolder extends BaseMuiltyViewHolder<PictureBean> {

    @Override
    public void init(final PictureBean pictureBean, BaseViewHolder helper, final Context context) {
        try {
            helper.setText(R.id.tv_news_title, pictureBean.getTitle());

            if(pictureBean.getList() != null && pictureBean.getList().size()>0) {
                Glide.with(context).load(pictureBean.getList().get(0).getMiddle())
                        .into(((ImageView) helper.getView(R.id.iv_image)));
            }
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    context.startActivity(VideoItemDetailActivity.getIntent(context, videoBean));
                    Intent intent = new Intent(context , PictureDetailActivity.class);
                    intent.putExtra("title" , pictureBean.getTitle());
                    intent.putParcelableArrayListExtra("imageBeanList" ,
                            (ArrayList<? extends Parcelable>) pictureBean.getList());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
