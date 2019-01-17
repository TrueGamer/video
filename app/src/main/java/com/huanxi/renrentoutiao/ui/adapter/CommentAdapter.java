package com.huanxi.renrentoutiao.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.NewsCommentBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

import java.util.List;

/**
 * Created by Dinosa on 2018/3/1.
 */


//这里我们将评论做成一个RecyclerView；

public class CommentAdapter extends BaseQuickAdapter<NewsCommentBean,BaseViewHolder>{


    private final Activity mActivity;

    public CommentAdapter(Context context, @Nullable List<NewsCommentBean> data) {
        super(R.layout.item_vedio_comment, data);

        mActivity = ((Activity) context);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsCommentBean item) {

        NewsCommentBean newsCommentBean = (NewsCommentBean) item;
        ImageView ivIconUser=helper.getView(R.id.iv_icon_user);
        TextView mTvName=helper.getView(R.id.tv_name);
        TextView mTvAddressAndDate=helper.getView(R.id.tv_address_and_date);
        TextView mTvLikeNumber=helper.getView(R.id.tv_like_number);
        TextView mTvCommentContent=helper.getView(R.id.tv_comment_content);

        ImageUtils.loadImage(mActivity,newsCommentBean.getAvatar(),ivIconUser);
        mTvName.setText(newsCommentBean.getNickname());
        mTvAddressAndDate.setText(newsCommentBean.getAddtime());
        mTvLikeNumber.setText(newsCommentBean.getPraisenum());
        mTvCommentContent.setText(newsCommentBean.getContent());

    }

}
