package com.huanxi.renrentoutiao.ui.fragment.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.model.bean.AdVideoBean;
import com.huanxi.renrentoutiao.net.api.news.ApiNewsCommentList;
import com.huanxi.renrentoutiao.net.api.user.ApiInviteFriendDesc;
import com.huanxi.renrentoutiao.net.api.user.ApiUserDoLike;
import com.huanxi.renrentoutiao.net.api.video.ApiVedioList;
import com.huanxi.renrentoutiao.net.api.video.ApiVedioListDetail;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendDesc;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.net.bean.comment.ResNewsCommentList;
import com.huanxi.renrentoutiao.net.bean.video.ResVideoList;
import com.huanxi.renrentoutiao.presenter.LoginPresenter;
import com.huanxi.renrentoutiao.ui.activity.WebHelperActivity;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.AdBean;
import com.huanxi.renrentoutiao.ui.adapter.AdsAdapter;
import com.huanxi.renrentoutiao.ui.adapter.VideoDetailAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.VideoListAdapter;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.huanxi.renrentoutiao.ui.media.TTFeedGroupPicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedLargePicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedSmallPicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedVideoAd;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhxu.library.utils.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import cn.tongdun.android.shell.db.utils.LogUtil;

/**
 * Created by Dinosa on 2018/3/6.
 */

public class VideoDetailFragment extends BaseLoadingFrament {


    public static final String HOME_TAB_ID = "home_tab_id";
    @BindView(R.id.rv_home)
    RecyclerView mRvHome;

 /*   @BindView(R.id.fl_banner_container)
    FrameLayout mFlBannerContainer;*/

    @BindView(R.id.ns_float_ad)
    NestedScrollView mNsFloatView;

    @BindView(R.id.rv_float_ad)
    RecyclerView mRvFloatAd;

    @BindView(R.id.rl_refreshLayout)
    EasyRefreshLayout mRlRefreshLayout;

    //这里是底部的popupWindow;
    @BindView(R.id.ll_popwindow_container)
    View mLlPopupWindowContainer;

    @BindView(R.id.tv_pop_title)
    TextView mTvPopTitle;

    @BindView(R.id.tv_pop_content)
    TextView mTvPopContent;

    @BindView(R.id.iv_close)
    View mIvPopClose;

    @BindView(R.id.rl_pop_window)
    View mRlPopWidow;



    TextView mTvTitle;

    BannerView mRecyclerViewBv;
    BannerView mAdFloatView;

    LinearLayout mLlAdBannerContainer;
    private View mLlTitleContainer;
    private View mNsView;
    private String homeTabId;


    @Override
    public void onRetry() {
        requestAdapterData(true);
    }

    VideoDetailAdapter mVideoDetailAdapter;

    //这里是获取得到content的loadingLayout;
    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.fragment_video_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        if(getArguments() != null) {
            homeTabId = getArguments().getString(HOME_TAB_ID);
        }

        mRvHome.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRvHome.setAdapter(getAdapter());

        mRlRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                requestNextAdapterData();
            }

            @Override
            public void onRefreshing() {
                requestAdapterData(false);
            }
        });
        mRvHome.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int totalDy=0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //这里我们要记录的
                if(isHasAds()){
                    totalDy+=dy;
                    if (mLlTitleContainer != null) {
                        //如果大于
                        if(totalDy >= mLlTitleContainer.getHeight()){
                            //这里我们就让我们的广告显示出来；
                            //mFlBannerContainer.setVisibility(View.VISIBLE);
                            mNsFloatView.setVisibility(View.VISIBLE);
                        }else{
                            mNsFloatView.setVisibility(View.INVISIBLE);
                            //一隐藏；
                            //mFlBannerContainer.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });
    }

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
            View footerView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_news_comment_footer, getRvHome(), false);

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

        mLlTitleContainer = headerView.findViewById(R.id.ll_title_container);
        mTvTitle = (TextView) headerView.findViewById(R.id.tv_title);

        //这里是广告的逻辑
        mNsView = headerView.findViewById(R.id.ns_view);


        //mLlAdBannerContainer = (LinearLayout) headerView.findViewById(R.id.bannerContainer);

        //是否有广告显示；
        if(isHasAds()){
            mNsView.setVisibility(View.VISIBLE);
        }else{
            mNsView.setVisibility(View.GONE);
        }


    }

    LoginPresenter mLoginPresenter;

    @Override
    protected void initData() {

        mLoginPresenter = new LoginPresenter(getBaseActivity(), new LoginPresenter.OnLoginListener() {
            @Override
            public void onLoginSuccess() {
                dismissDialog();
                getNavigationData();
            }

            @Override
            public void onLoginStart() {
                showDialog();
            }

            @Override
            public void onLoginFaild() {
                dismissDialog();
            }
        });

        getEasyRefreshLayout().setEnablePullToRefresh(false);

        mTvTitle.setText(mVideoDetail.getTitle());

        //初始化广告
       // initAds();
//        initAdsNew();
        loadListAd();
        //这里我们要做的一个操作就是显示banner
        requestAdapterData(true);

        getNavigationData();
    }

    /**
     * 获取Navigation里面的内容；
     */
    public void getNavigationData() {


        ApiInviteFriendDesc apiInviteFriendDesc = new ApiInviteFriendDesc(new HttpOnNextListener<ResInviteFriendDesc>() {

            @Override
            public void onNext(final ResInviteFriendDesc resInviteFriendDesc) {

                initRightBottomToast(resInviteFriendDesc);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, getBaseActivity(), new HashMap<String, String>());

        HttpManager.getInstance().doHttpDeal(apiInviteFriendDesc);
    }



    /**
     * 这里要做的一个操作就是显示登陆领取红包的一个操作的；
     *
     * @param resInviteFriendDesc
     */
    public void initRightBottomToast(final ResInviteFriendDesc resInviteFriendDesc) {

        String showTextForLoginDetail = resInviteFriendDesc.getShowTextForLoginDetail();
        if ("1".equals(showTextForLoginDetail)) {

            mLlPopupWindowContainer.setVisibility(View.GONE);

            mIvPopClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLlPopupWindowContainer.setVisibility(View.GONE);
                }
            });

            //这里要做的一个逻辑就是显示
            if(isLogin()){

                mTvPopTitle.setText(resInviteFriendDesc.getTextforloginDetail().getTitle());
                mTvPopContent.setText(resInviteFriendDesc.getTextforloginDetail().getCont());

                mRlPopWidow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!TextUtils.isEmpty(resInviteFriendDesc.getTextforloginDetail().getUrl())) {

                            Intent intent = WebHelperActivity.getIntent(
                                    getBaseActivity(),
                                    resInviteFriendDesc.getTextforloginDetail().getUrl(),
                                    resInviteFriendDesc.getTextforloginDetail().getTitle(),
                                    false);
                            startActivity(intent);
                        }

                    }
                });


            }else{
                //登陆的逻辑
                String title="<font color='#be6c14'>微信登陆领取<font color='#f13021' ><b>1000</b></font>金币</font>";
                String content="<font color='#be6c14'>海量<font color='#f13021'>任务</font>、限时<font color='#f13021'>红包</font>、边看<font color='#f13021'>边赚</font>!</font>";
                //没有登陆的逻辑
                mTvPopTitle.setText(Html.fromHtml(title));
                mTvPopContent.setText(Html.fromHtml(content));

                mRlPopWidow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoginPresenter.login();
                    }
                });

            }
        }else{
            mLlPopupWindowContainer.setVisibility(View.GONE);
        }
    }

    LinkedList<TTFeedAd> mData;

    /**
     * 加载feed广告
     */
    private void loadListAd() {
        mData = new LinkedList<>();
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance(getActivity());
        TTAdNative mTTAdNative = ttAdManager.createAdNative(getActivity());
        TTAdManagerHolder.getInstance(getActivity()).requestPermissionIfNecessary(getActivity());

        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(ConstantAd.CSJAD.VIDEO_DETAIL_ID)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(5)
                .build();
        //调用feed广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtil.e("info","loadFeedAd error "+message);
                loadRecommendData();
            }
            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                LogUtil.d("info","onFeedAdLoad ads=== "+ads.size());
                mData.clear();
                mData.addAll(ads);
                loadRecommendData();
            }
        });
    }

    List<AdBean> videoAdList;

    private void loadRecommendData() {

        HashMap<String, String> paramsMap = new HashMap<>();

        if(TextUtils.isEmpty(homeTabId))
            throw new IllegalArgumentException("homeTabId is null");
        paramsMap.put(ApiVedioList.CATEGORY, homeTabId);

        paramsMap.put("device_id", SystemUtils.getIMEI(getActivity()));
        paramsMap.put("device_platform", "android");
        paramsMap.put("device_type", SystemUtils.getSystemModel());
        paramsMap.put("device_brand", SystemUtils.getDeviceBrand());
        paramsMap.put("os_api", SystemUtils.getSystemApi());
        paramsMap.put("os_version", SystemUtils.getSystemVersion());
        paramsMap.put("uuid", SystemUtils.getIMEI(getActivity()));
        paramsMap.put("openudid", SystemUtils.getOpenUid(getActivity()));
        paramsMap.put("resolution", SystemUtils.getResolution(getActivity()));
        paramsMap.put("dpi", SystemUtils.getDensity(getActivity()));
        paramsMap.put(ApiVedioList.PAGE_NUM, "1");
        ApiVedioList apiVedioListDetail = new ApiVedioList(new HttpOnNextListener<ResVideoList>() {
            @Override
            public void onNext(ResVideoList vedioDataBeen) {

                List<MultiItemEntity> multiItemEntities = new ArrayList<>();
                int count = 0;
                for(VideoBean bean : vedioDataBeen.getList()) {
                    if(bean.isAd()) continue;
                    if(count > 5) break;
                    MultiItemEntity entity = getCstAd();
                    if(entity != null) {
                        multiItemEntities.add(entity);
                    }
                    multiItemEntities.add(getVideo(bean));
                    count++;
                }

                RecyclerView adRecyclerView = (RecyclerView) mNsView.findViewById(R.id.rv_ads);

                adRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });

                VideoListAdapter adapter = new VideoListAdapter(multiItemEntities,homeTabId);
                adRecyclerView.setAdapter(adapter);
            }
        },paramsMap,(RxAppCompatActivity) getActivity());
        HttpManager.getInstance().doHttpDeal(apiVedioListDetail);
    }

    private MultiItemEntity getVideo(VideoBean videoBean) {

        //这里表示是视频的逻辑
        MultiItemEntity multiItemEntity = null;

        String title = videoBean.getContent().getTitle();
        String source = videoBean.getContent().getSource();
        String urlMd5 = videoBean.getUrlmd5();
        String imageUrl = "";
        String item_id = videoBean.getContent().getItem_id();
        String group_id = videoBean.getContent().getGroup_id();
        String video_id = videoBean.getContent().getVideo_id();
        String publishTime = videoBean.getContent().getPublish_time();
        Long duration = videoBean.getContent().getVideo_duration();

        try {
            if(videoBean.getContent().getLarge_image_list() != null) {
                imageUrl = videoBean.getContent().getLarge_image_list().get(0).getUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        multiItemEntity = new VideoListBean(source, urlMd5, title, imageUrl, item_id, group_id, video_id, publishTime, duration);

        return multiItemEntity;
    }

    /**
     * 这里是获取穿山甲的广告
     *
     * @return
     */
    private MultiItemEntity getCstAd() {

        MultiItemEntity multiItemEntity = null;

        if(mData != null && mData.size()>0) {
            TTFeedAd ttFeedAd = mData.removeFirst();

            String title = ttFeedAd.getTitle();
            String des = ttFeedAd.getDescription();
            String source = ttFeedAd.getSource();
            TTImage icon = ttFeedAd.getIcon();
            List<TTImage> imageList = ttFeedAd.getImageList();
            int interactionType = ttFeedAd.getInteractionType();
            int imageMode = ttFeedAd.getImageMode();
            View view = ttFeedAd.getAdView();

            Log.i("info" , "ads-------"+title+",imageMode="+imageMode+",icon="+icon.getImageUrl());
            if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
                TTFeedSmallPicAd smallPicAd = new TTFeedSmallPicAd(title , des , source , icon
                        , imageList , interactionType , imageMode , view);
                smallPicAd.setTtFeedAd(ttFeedAd);
//                smallPicAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = smallPicAd;
            } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
                TTFeedLargePicAd largePicAd = new TTFeedLargePicAd(title , des , source , icon
                        , imageList , interactionType , imageMode , view);
                largePicAd.setTtFeedAd(ttFeedAd);
//                largePicAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = largePicAd;
            } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
                TTFeedGroupPicAd groupPicAd = new TTFeedGroupPicAd(title , des , source , icon
                        , imageList , interactionType , imageMode , view);
                groupPicAd.setTtFeedAd(ttFeedAd);
//                groupPicAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = groupPicAd;
            } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
                TTFeedVideoAd videoAd = new TTFeedVideoAd(title , des , source , icon
                        , imageList , interactionType , imageMode , view);
                videoAd.setTtFeedAd(ttFeedAd);
//                videoAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = videoAd;
            }
        }

        return multiItemEntity;
    }

    private void initAdsNew() {
        LogUtil.d("info","initAdsNew");
        //这里是header广告；
        RecyclerView adRecyclerView = (RecyclerView) mNsView.findViewById(R.id.rv_ads);

        adRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        ResSplashAds resAds = getBaseActivity().getMyApplication().getResAds();
        List<AdVideoBean> videoList1 = resAds.getVideodetail();
        List<AdVideoBean> videoList2 = resAds.getVideodetail2();
        List<AdVideoBean> videoList3 = resAds.getVideodetail3();
        List<AdVideoBean> videoList4 = resAds.getVideodetail4();
        List<AdVideoBean> videoList5 = resAds.getVideodetail5();
        List<AdVideoBean> videoList6 = resAds.getVideodetail6();
        List<AdVideoBean> videoList7 = resAds.getVideodetail7();
        List<AdVideoBean> videoList8 = resAds.getVideodetail8();
        List<AdVideoBean> videoList9 = resAds.getVideodetail9();
        List<AdVideoBean> videoList10 = resAds.getVideodetail10();

        videoAdList = new ArrayList<>();
//        AdBean adBean1 = new AdBean();
//        adBean1.setType("gdt");
//        adBean1.setAdtype("upimgdowntext");
//        setAd(videoList2);
//        setAd(videoList3);
//        setAd(videoList1);
//        setAd(videoList4);
//        setAd(videoList5);
//        setAd(videoList6);
//        setAd(videoList7);
//        setAd(videoList8);
//        setAd(videoList9);
//        setAd(videoList10);
        LogUtil.d("info","initAdsNew 111 videoAdList "+videoAdList);
        LogUtil.d("info","mData size "+mData.size()+" mData "+mData);
        for(int i = 0; i < mData.size() && i < 5; i++) {
            AdBean adBean1 = new AdBean();
            adBean1.setType(AdBean.TYPE_CSJ);
            adBean1.setTtFeedAd(mData.get(i));
            videoAdList.add(adBean1);
        }
        LogUtil.d("info","initAdsNew 222 videoAdList "+videoAdList);
        AdsAdapter adsAdapter = new AdsAdapter(videoAdList); //resAds.getVideodetail()
        adRecyclerView.setAdapter(adsAdapter);

        //悬浮的广告；
//        AdsAdapter floatAdapter = new AdsAdapter(videoAdList); //resAds.getVideodetail()
//        mRvFloatAd.setLayoutManager(new LinearLayoutManager(getBaseActivity()){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        });
//        mRvFloatAd.setAdapter(floatAdapter);
    }

    int number = 0;
    private void setAd(List<AdVideoBean> list) {
        if(list != null && list.size()>0) {
            for(int i=0;i<list.size();i++) {
                AdBean adBean1 = new AdBean();
                LogUtil.d("info","adbean type: "+list.get(i).getType());
//                if("gdt".equals(list.get(i).getType())) {
//                    adBean1.setType("gdt");
//                    adBean1.setAdtype("upimgdowntext");
////                    list.set(i , adBean1);
//                } else if ("csj".equals(list.get(i).getType())){
                    if(mData != null) {
                        if(mData.size() > i) {
                            LogUtil.d("info","mData size large than i:"+i+" number: "+number);
                            adBean1.setType("csj");
                            adBean1.setTtFeedAd(mData.get(i));
                        } else {
                            LogUtil.d("info","mData size less than i:"+i);
                            adBean1.setType("csj");
                            adBean1.setTtFeedAd(mData.get(0));
                        }
//                        list.set(i , adBean1);
                    }
//                } else if ("ta".equals(list.get(i).getType())) {
//                    adBean1.setType("ta");
//                    adBean1.setAdtype("upimgdowntext");
//                } else if ("baidu".equals(list.get(i).getType())) {
//                    adBean1.setType("baidu");
//                }
                videoAdList.add(adBean1);
            }
//            videoAdList.addAll(list);
        }
    }


    @Deprecated
    private void initAds() {
        //这里是RecyclerView里面的Banner;m
        mRecyclerViewBv = new BannerView(getBaseActivity(), ADSize.BANNER, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.BANNER_AD);
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        mRecyclerViewBv.setRefresh(0);
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

        //这里要初始化悬浮的广告
       mAdFloatView = new BannerView(getBaseActivity(), ADSize.BANNER, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.BANNER_AD);
        mAdFloatView.setRefresh(0);
        mAdFloatView.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i(
                        "AD_DEMO",
                        String.format("Banner onNoAD，eCode = %d, eMsg = %s", error.getErrorCode(),
                                error.getErrorMsg()));
                mAdFloatView.loadAD();
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
       // mFlBannerContainer.addView(mAdFloatView);
        //这里是需要初始化；隐藏的悬浮的广告；
        mAdFloatView.loadAD();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRecyclerViewBv != null) {
            mRecyclerViewBv.destroy();
        }

        if (mAdFloatView != null) {
            mAdFloatView.destroy();
        }

    }

    //这里是之前的获取的评论的接口的；
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
                getEasyRefreshLayout().loadMoreComplete();
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
            mActivity.getTipView().setText(getCommentCount() + "");
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

        return mVideoDetailAdapter.getData().size();
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
                getEasyRefreshLayout().loadMoreComplete();
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
                    mPage++;
                }

                updateCommentTip();
                getEasyRefreshLayout().loadMoreComplete();
            }
        }, mActivity, paramsMap);
        HttpManager.getInstance().doHttpDeal(apiNewsCommentList);
    }

    public void showFooterText(String text) {

        mVideoDetailAdapter.getFooterLayout().setVisibility(View.VISIBLE);
        TextView tvText = (TextView) mVideoDetailAdapter.getFooterLayout().findViewById(R.id.tv_is_can_pull);
        tvText.setText(text);
    }


    public void requestNextAdapterData() {
        getMoreComment();
    }


    //这里我们实现
    public static String VIDEDO_DETAIL = "videoDetail";
    private VideoListBean mVideoDetail;
    private VideoItemDetailActivity mActivity;

    //这里是视频的详情；
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mVideoDetail = (VideoListBean) arguments.getSerializable(VIDEDO_DETAIL);
    }

    public static VideoDetailFragment getFragment(VideoListBean detail,String homeTabId) {
        VideoDetailFragment videoItemDetailFragment = new VideoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VIDEDO_DETAIL, detail);
        bundle.putString(HOME_TAB_ID,homeTabId);
        videoItemDetailFragment.setArguments(bundle);
        return videoItemDetailFragment;
    }



    public EasyRefreshLayout getEasyRefreshLayout(){
        return mRlRefreshLayout;
    }

    public RecyclerView getRvHome() {
        return mRvHome;
    }


    /**
     * 这里我们执行点赞的逻辑
     */
    public void doOnClickLike(final String commId){

        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {

                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put(ApiUserDoLike.FROM_UID,getUserBean().getUserid());
                paramsMap.put(ApiUserDoLike.COMMENT_ID,commId);

                //这里执行的对应的逻辑；
                ApiUserDoLike apiUserDoLike=new ApiUserDoLike(new HttpOnNextListener<ResEmpty>() {

                    @Override
                    public void onNext(ResEmpty resEmpty) {
                        //获取
                        requestAdapterData(false);
                    }

                },getBaseActivity(),paramsMap);

                HttpManager.getInstance().doHttpDeal(apiUserDoLike);
            }

            @Override
            public void loginFaild() {

            }
        });

    }

}
