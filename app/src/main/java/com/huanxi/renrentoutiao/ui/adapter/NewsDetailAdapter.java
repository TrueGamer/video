package com.huanxi.renrentoutiao.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.model.bean.NewsCommentBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.news.NewsDetailActivity2;
import com.huanxi.renrentoutiao.ui.adapter.bean.NewsDetailComments;
import com.huanxi.renrentoutiao.ui.adapter.bean.NewsRecommentBean;
import com.huanxi.renrentoutiao.utils.ImageUtils;

import java.util.List;

/**
 * Created by Dinosa on 2018/1/27.
 */

public class NewsDetailAdapter extends BaseAdsAdapter{

    public static final int TYPE_AD = 1;
    public static final int TYPE_TITLE = 2;
    public static final int TYPE_COMMENT = 3;
    public static final int TYPE_RECOMMENT = 4;
    public static final int TYPE_NEW_COMMENT = 5;

    private final NewsDetailActivity2 mActivity;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsDetailAdapter(BaseActivity activity, List<MultiItemEntity> data) {
        super(data);
        mActivity = (NewsDetailActivity2)activity;
        addItemType(TYPE_AD, R.layout.item_gdt_banner_ad);
        addItemType(TYPE_TITLE, R.layout.item_vedio_detail_comment_title);
        addItemType(TYPE_COMMENT, R.layout.item_vedio_comment);
        addItemType(TYPE_RECOMMENT, R.layout.item_layout_relvant_recomm);
        addItemType(TYPE_NEW_COMMENT, R.layout.layout_news_comment);

    }

    CommentAdapter mCommentAdapter;
    RecyclerView mRvComments;



    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        super.convert(helper,item);
        if (item instanceof AdBean) {

        }else{

            if (helper.getItemViewType() == TYPE_COMMENT) {
                //这里我们要对数据进行一个填充；
                final NewsCommentBean newsCommentBean = (NewsCommentBean) item;
                ImageView ivIconUser=helper.getView(R.id.iv_icon_user);
                TextView mTvName=helper.getView(R.id.tv_name);
                TextView mTvAddressAndDate=helper.getView(R.id.tv_address_and_date);
                TextView mTvLikeNumber=helper.getView(R.id.tv_like_number);
                TextView mTvCommentContent=helper.getView(R.id.tv_comment_content);

                ImageUtils.loadImage(mActivity,newsCommentBean.getAvatar(),ivIconUser);
                mTvName.setText(newsCommentBean.getNickname());
                mTvAddressAndDate.setText(newsCommentBean.getAddtime());

                mTvLikeNumber.setText(newsCommentBean.getPraisenum()==null?"0":newsCommentBean.getPraisenum());
                mTvCommentContent.setText(newsCommentBean.getContent());

                ImageView view = (ImageView) helper.getView(R.id.iv_prise);
                if (newsCommentBean.getHaspraise()>0) {
                    view.setImageResource(R.drawable.icon_prise_dolike);
                }else{
                    view.setImageResource(R.drawable.icon_prise_undo);
                }

                helper.getView(R.id.ll_do_like).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //这里表示点赞的逻辑；
                        //如果没有登陆是需要跳转到登陆的页面的；
                        mActivity.checkLogin(new BaseActivity.CheckLoginCallBack() {
                            @Override
                            public void loginSuccess() {
                                //这里执行点赞的逻辑；
                                mActivity.getPresenter().onClickDoLike(mActivity.getUserBean().getUserid(),newsCommentBean.getId());
                            }

                            @Override
                            public void loginFaild() {

                            }
                        });
                    }
                });


            }else if(helper.getItemViewType() == TYPE_RECOMMENT){


                NewsRecommentBean recommentBean = (NewsRecommentBean) item;

                RecyclerView rvRecomment = (RecyclerView) helper.getView(R.id.rv_recommments);

                if (rvRecomment.getLayoutManager() == null) {
                    rvRecomment.setLayoutManager(new LinearLayoutManager(mContext){
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });
                }

                if(rvRecomment.getAdapter() == null){
                    HomeTabFragmentAdapter recommentAdapter = new HomeTabFragmentAdapter((BaseActivity) mContext,recommentBean.getNewsItemBeen());
                    rvRecomment.setAdapter(recommentAdapter);
                }

            }else if(helper.getItemViewType() == TYPE_NEW_COMMENT){

                NewsDetailComments detailCommentsDetail = (NewsDetailComments) item;

                //这里我们是需要填充全部的内容的；
                mRvComments = (RecyclerView) helper.itemView.findViewById(R.id.rv_comments);

                if (mRvComments.getLayoutManager() == null) {

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
                }

                if (mRvComments.getAdapter() == null) {
                    mCommentAdapter = (CommentAdapter) mRvComments.getTag(R.id.tag_adapter);
                    if(mCommentAdapter==null){

                        mCommentAdapter = new CommentAdapter(mActivity,detailCommentsDetail.getList());

                        View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.item_news_comment_empty, mRvComments, false);

                        mCommentAdapter.setEmptyView(emptyView);

                        mRvComments.setTag(R.id.tag_adapter,mCommentAdapter);
                    }
                    mRvComments.setAdapter(mCommentAdapter);
                }
            }
        }
    }

    public void notifyComments(List<NewsCommentBean> mCommentBean,boolean isCanPull,boolean isFirst){

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

    /**
     * 这里是获取评论的数;
     */
    public int getCommentsCount(){

        if (mCommentAdapter != null) {
            return mCommentAdapter.getData().size();
        }else{
            return 0;
        }
    }

    //=========这里我们重写刷新评论和 添加评论的方法，防止刷新WebView内容；=============

    /**
     * 这里我们要刷新评论的列表；
     */
    public void refreshComment(List<NewsCommentBean> list){
        //这里要执行的逻辑就是刷新评论的列表；
        List<NewsCommentBean> data = getData();

        notifyItemRangeRemoved(1,data.size());

        data.clear();
        data.addAll(list);

        notifyItemRangeInserted(1,data.size());
       //notifyItemRangeChanged(1,0);
    }

    /**
     * 这里是添加评论的一个操作；
     * @param list
     */
    public void addAllComment(List<NewsCommentBean> list){
        //这里要执行的逻辑就是刷新评论的列表；
        List<NewsCommentBean> data = getData();
        int start=data.size();
        data.addAll(list);
        notifyItemRangeInserted(start,list.size());
    }


}
