package com.huanxi.renrentoutiao.ui.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.net.api.news.ApiNewsCommentList;
import com.huanxi.renrentoutiao.net.bean.comment.ResNewsCommentList;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.VideoDetailAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.NewsDetailComments;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingRecyclerViewFragment;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

/**
 * Created by Dinosa on 2018/1/25.
 */

public class VideoItemDetailFragment extends BaseLoadingRecyclerViewFragment {

    private VideoDetailAdapter mVideoDetailAdapter;
    private VideoListBean mVideoDetail;
    private VideoItemDetailActivity mActivity;
    private NewsDetailComments mNewsDetailComments;
    private TextView mTvTitle;
    private LinearLayout mLlAdBannerContainer;

    BannerView mRecyclerViewBv;

    @Override
    public RecyclerView.Adapter getAdapter() {

        if (mVideoDetailAdapter == null) {
            mVideoDetailAdapter = new VideoDetailAdapter(getActivity(), null);
            View headerView = View.inflate(getBaseActivity(), R.layout.header_item_video_detail, null);
            initHeaderView(headerView);
            mVideoDetailAdapter.addHeaderView(headerView);

            mVideoDetailAdapter.setHeaderAndEmpty(true);
            //添加头view,footeView，和emptyView;

            View emptyView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_news_comment_empty, getRvHome(), false);
            mVideoDetailAdapter.setEmptyView(emptyView);

            //这里我们是需要修改footer里面的页面的；
            View footerView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_news_comment_footer,getRvHome() , false);

            mVideoDetailAdapter.setFooterView(footerView);

            mVideoDetailAdapter.setHeaderAndEmpty(true);
        }
        return mVideoDetailAdapter;
    }

    /**
     * 这里我们要初始化headerView；
     *
     * @param headerView
     */
    private void initHeaderView(View headerView) {

        mTvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        mLlAdBannerContainer = (LinearLayout) headerView.findViewById(R.id.bannerContainer);

    }

    @Override
    protected void initData() {

        //getEasyRefreshLayout().setEnablePullToRefresh(false);
        setRefreshEnable(false);
        mTvTitle.setText(mVideoDetail.getTitle());

        //初始化广告
        initAds();
        //这里我们要做的一个操作就是显示banner
        super.initData();
    }



    private void initAds() {
        //这里是RecyclerView里面的Banner;
        mRecyclerViewBv = new BannerView(getBaseActivity(), ADSize.BANNER, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.BANNER_AD);
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        mRecyclerViewBv.setRefresh(30);
        mRecyclerViewBv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i(
                        "AD_DEMO",
                        String.format("Banner onNoAD，eCode = %d, eMsg = %s", error.getErrorCode(),
                                error.getErrorMsg()));
                mRecyclerViewBv.loadAD();
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        mLlAdBannerContainer.addView(mRecyclerViewBv);
        //这里是需要初始化；隐藏的悬浮的广告；
        mRecyclerViewBv.loadAD();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRecyclerViewBv != null) {
            mRecyclerViewBv.destroy();
        }
    }

    //这里是之前的获取的评论的接口的；
    @Override
    public void requestAdapterData(boolean isFirst) {
        mActivity = ((VideoItemDetailActivity) getActivity());
        //这里我们要做的一个操作
        //获取评论
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiNewsCommentList.ISSUE_ID, mVideoDetail.getUrlmd5());
        paramsMap.put(ApiNewsCommentList.PAGE_NUMBER, 1 + "");

        ApiNewsCommentList apiNewsCommentList = new ApiNewsCommentList(new HttpOnNextListener<ResNewsCommentList>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //getEasyRefreshLayout().loadMoreComplete();
                loadMoreComplete();
            }

            @Override
            public void onNext(ResNewsCommentList resNewsCommentList) {

                if (resNewsCommentList.getList() != null && resNewsCommentList.getList().size() > 0) {
                    //这里我们要做的一个逻辑就是
                    mVideoDetailAdapter.getFooterLayout().setVisibility(View.VISIBLE);
                    TextView tvText = (TextView) mVideoDetailAdapter.getFooterLayout().findViewById(R.id.tv_is_can_pull);

                    if (resNewsCommentList.getList().size() < 20) {
                        //这里表示没有分页数据了
                        //这里要设置显示的文字；
                        tvText.setText("我是有底线的");
                    } else {
                        tvText.setText("上拉有彩蛋");
                    }
                    mVideoDetailAdapter.replaceData(resNewsCommentList.getList());
                    mPage = 2;
                } else {
                    //这里服务器没有数据；
                    mVideoDetailAdapter.getFooterLayout().setVisibility(View.GONE);
                }

                updateCommentTip();

                showSuccess();

            }
        }, mActivity, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiNewsCommentList);
    }

    private void updateCommentTip() {
        if (getCommentCount() > 0) {
            mActivity.getTipView().setVisibility(View.VISIBLE);
            mActivity.getTipView().setText(mVideoDetailAdapter.getData().size() + "");
        } else {
            mActivity.getTipView().setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取评论的数量；
     *
     * @return
     */
    public int getCommentCount() {
        int totalSize = mVideoDetailAdapter.getData().size();
        int emptyViewCount = mVideoDetailAdapter.getEmptyViewCount();
        int footerLayoutCount = mVideoDetailAdapter.getFooterLayoutCount();
        int headerLayoutCount = mVideoDetailAdapter.getHeaderLayoutCount();
        return totalSize-emptyViewCount-footerLayoutCount-headerLayoutCount;
    }


    int mPage = 1;

    /**
     * 获取更多的评论
     */
    public void getMoreComment() {
        //获取评论
        HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put(ApiNewsCommentList.ISSUE_ID, mVideoDetail.getUrlmd5());
        paramsMap.put(ApiNewsCommentList.PAGE_NUMBER, mPage + "");

        ApiNewsCommentList apiNewsCommentList = new ApiNewsCommentList(new HttpOnNextListener<ResNewsCommentList>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //getEasyRefreshLayout().loadMoreComplete();
                loadMoreComplete();
            }

            @Override
            public void onNext(ResNewsCommentList resNewsCommentList) {

                if (resNewsCommentList.getList() != null && resNewsCommentList.getList().size() > 0) {
                    //mNewsDetailComments.setList(resNewsCommentList.getList());
                    //这里我们要做的一个逻辑就是

                    if (resNewsCommentList.getList().size() < 20) {
                        //这里表示没有分页数据了
                        //这里要设置显示的文字；
                        showFooterText("我是有底线的");
                    } else {
                        showFooterText("上拉有彩蛋");
                    }
                    mVideoDetailAdapter.addData(resNewsCommentList.getList());
                    mPage ++;
                }

                updateCommentTip();
                //getEasyRefreshLayout().loadMoreComplete();
                loadMoreComplete();
            }
        }, mActivity, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiNewsCommentList);
    }

    public void showFooterText(String text){

        mVideoDetailAdapter.getFooterLayout().setVisibility(View.VISIBLE);
        TextView tvText = (TextView) mVideoDetailAdapter.getFooterLayout().findViewById(R.id.tv_is_can_pull);
        tvText.setText(text);
    }


    @Override
    public void requestNextAdapterData() {
        getMoreComment();
    }


    public static String VIDEDO_DETAIL = "videoDetail";


    //这里是视频的详情；
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mVideoDetail = (VideoListBean) arguments.getSerializable(VIDEDO_DETAIL);
    }

    public static VideoItemDetailFragment getFragment(VideoListBean detail) {
        VideoItemDetailFragment videoItemDetailFragment = new VideoItemDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VIDEDO_DETAIL, detail);
        videoItemDetailFragment.setArguments(bundle);
        return videoItemDetailFragment;
    }

}
