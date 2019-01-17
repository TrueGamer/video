package com.huanxi.renrentoutiao.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.NewsCommentBean;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.NewsDetailComments;
import com.huanxi.renrentoutiao.ui.view.ads.GDTBannerView;
import com.huanxi.renrentoutiao.utils.ImageUtils;

import java.util.List;

/**
 * Created by Dinosa on 2018/3/1.
 */

public class VideoDetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    //这里我们有四种类型；
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_AD = 1; // 广点通
    public static final int TYPE_TITLE = 2;
    public static final int TYPE_COMMENT = 3;
    public static final int TYPE_COMMENT_NEW = 5;   //这里要与新闻同步

    public static final int TYPE_AD_CSJ = 6;// 穿山甲

    private final Context mActivity;


    CommentAdapter mCommentAdapter;
    RecyclerView mRvComments;
    private NewsDetailComments mDetailCommentsDetail;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public VideoDetailAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        mActivity = context;
        addItemType(TYPE_HEADER, R.layout.item_vedio_detail_header);
        addItemType(TYPE_AD, R.layout.item_video_detail_ad_container);
        addItemType(TYPE_TITLE, R.layout.item_vedio_detail_comment_title);
        addItemType(TYPE_COMMENT, R.layout.item_vedio_comment);
        addItemType(TYPE_COMMENT_NEW, R.layout.layout_news_comment);

        addItemType(TYPE_AD_CSJ , R.layout.listitem_ad_large_pic);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {

        switch (helper.getItemViewType()) {
            case TYPE_HEADER:

                VideoItemHeader itemHeader = (VideoItemHeader) item;
                helper.setText(R.id.tv_title,itemHeader.getVideDesc());

                break;
            case TYPE_AD:
                //这里我们显示广告；
                GDTBannerView gdtBannerView = new GDTBannerView(mActivity);
                gdtBannerView.init();
                ((ViewGroup) helper.itemView).addView(gdtBannerView);
                break;
            case TYPE_TITLE:
                break;
            case TYPE_COMMENT:
                final NewsCommentBean bean = (NewsCommentBean) item;
                //然后这里我们做相应的操作；
                ImageView userIcon = (ImageView) helper.getView(R.id.iv_icon_user);
                ImageUtils.loadImage(mActivity,bean.getAvatar(),userIcon);
                helper.setText(R.id.tv_name,bean.getNickname()+"");
                helper.setText(R.id.tv_like_number,bean.getPraisenum()==null?"0":bean.getPraisenum());
                helper.setText(R.id.tv_comment_count,"");
                helper.setText(R.id.tv_address_and_date,bean.getAddtime());
                helper.setText(R.id.tv_comment_content,bean.getContent());
                //这里要设置对应的逻辑；
                ImageView view = (ImageView) helper.getView(R.id.iv_prise);
                if (bean.getHaspraise()>0) {
                    view.setImageResource(R.drawable.icon_prise_dolike);
                }else{
                    view.setImageResource(R.drawable.icon_prise_undo);
                }

                helper.getView(R.id.ll_do_like).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //这里要执行的逻辑就是点赞的逻辑
                        ((VideoItemDetailActivity) mActivity).getVideoDetailFragment().doOnClickLike(bean.getId());
                    }
                });
                break;
            case TYPE_COMMENT_NEW:

                //这里操作；
                mDetailCommentsDetail = (NewsDetailComments) item;
                //这里我们是需要填充全部的内容的；
                mRvComments = (RecyclerView) helper.itemView.findViewById(R.id.rv_comments);


                LinearLayoutManager tag = (LinearLayoutManager) mRvComments.getTag(R.id.tag_linearlayou_manager);
                if(tag==null){
                    tag=new LinearLayoutManager(mActivity){
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    mRvComments.setTag(R.id.tag_linearlayou_manager,tag);
                }
                mRvComments.setLayoutManager(tag);

                mCommentAdapter = (CommentAdapter) mRvComments.getTag(R.id.tag_adapter);
                if(mCommentAdapter==null){

                    mCommentAdapter = new CommentAdapter(mActivity, mDetailCommentsDetail.getList());

                    View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.item_news_comment_empty, mRvComments, false);

                    mCommentAdapter.setEmptyView(emptyView);

                    mRvComments.setTag(R.id.tag_adapter,mCommentAdapter);
                }
                if (mDetailCommentsDetail.getList()!=null && mDetailCommentsDetail.getList().size()>0) {


                    //这里我们是需要修改footer里面的页面的；
                    View footerView = LayoutInflater.from(mActivity).inflate(R.layout.item_news_comment_footer, mRvComments, false);
                    TextView textView = (TextView) footerView.findViewById(R.id.tv_is_can_pull);


                    List<NewsCommentBean> mCommentBean = mDetailCommentsDetail.getList();

                    if(mCommentBean.size()>0 && mCommentBean.size()<20){
                        textView.setText("我是有底线的");
                        mCommentAdapter.setFooterView(footerView);

                    }else if(mCommentBean.size() > 20){
                        textView.setText("上拉有彩蛋");
                        mCommentAdapter.setFooterView(footerView);
                    }

                }
                mRvComments.setAdapter(mCommentAdapter);

                break;
        }
    }


    public void notifyComments(List<NewsCommentBean> mCommentBean,boolean isFirst){

        if(mCommentAdapter == null){
            return;
        }
        mCommentAdapter.removeAllFooterView();

        //这里我们是需要修改footer里面的页面的；
        View footerView = LayoutInflater.from(mActivity).inflate(R.layout.item_news_comment_footer, mRvComments, false);
        TextView textView = (TextView) footerView.findViewById(R.id.tv_is_can_pull);


        if (isFirst) {
            if(mCommentBean.size()>0 && mCommentBean.size()<20){
                textView.setText("我是有底线的");
                mCommentAdapter.setFooterView(footerView);

            }else if(mCommentBean.size() > 20){
                textView.setText("上拉有彩蛋");
                mCommentAdapter.setFooterView(footerView);
            }
            mCommentAdapter.replaceData(mCommentBean);
        }else{
            mCommentAdapter.addData(mCommentBean);
        }
    }





    //头部的bean对象
    public static class VideoItemHeader implements MultiItemEntity{
        String videDesc;

        public VideoItemHeader(String videDesc) {
            this.videDesc = videDesc;
        }

        @Override
        public int getItemType() {
            return VideoDetailAdapter.TYPE_HEADER;
        }

        public String getVideDesc() {
            return videDesc;
        }

        public void setVideDesc(String videDesc) {
            this.videDesc = videDesc;
        }
    }

    //=========广告的================
    public static class VideoItemAd implements MultiItemEntity{

        @Override
        public int getItemType() {
            return VideoDetailAdapter.TYPE_AD;
        }

    }

    //========公共的title；===========
    public static class VideoItemCommentTitle implements MultiItemEntity{

        @Override
        public int getItemType() {
            return VideoDetailAdapter.TYPE_TITLE;
        }
    }


}
