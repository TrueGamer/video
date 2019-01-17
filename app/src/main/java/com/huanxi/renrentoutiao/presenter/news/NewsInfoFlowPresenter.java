package com.huanxi.renrentoutiao.presenter.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.baidu.mobad.feeds.NativeResponse;
import com.bytedance.sdk.openadsdk.DownloadStatusController;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.net.bean.news.ResNewsAndVideoBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.adhub.AdhubNativeBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.baidu.BaiDuBannerBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomBanner20_3Bean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomBigBannerBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomLeftTitlRightImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomTitleDownThreeImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.custom.CustomUpTitleDownImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GDTImgAds;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GdtBigBannerBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaLeftTitleRightImgAds;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaLeftTitleRightImgBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.news.NewsOneImageBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.news.NewsThreeImageBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.ui.media.TTFeedGroupPicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedLargePicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedSmallPicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedVideoAd;
import com.hubcloud.adhubsdk.NativeAd;
import com.hubcloud.adhubsdk.NativeAdListener;
import com.hubcloud.adhubsdk.NativeAdResponse;
import com.qq.e.ads.nativ.NativeExpressADView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.tongdun.android.shell.db.utils.LogUtil;

/**
 * Created by Dinosa on 2018/4/13.
 * 新闻信息流的业务逻辑类；这里将信息流的数据进行一个格式化的操作
 */

public class NewsInfoFlowPresenter {

    public LinkedList<NativeExpressADView> mGdtAdLists;
    private TaLeftTitleRightImgAds mTaLeftTitleRightImgAds;
    public LinkedList<TTFeedAd> mFeedAdList;
    private LinkedList<NativeAdResponse> mAdhubNativeData;
    private List<? extends View> mAdhubResponseViews;
    private NativeAd mNativeAd;
    private BaseActivity baseActivity;

    public NewsInfoFlowPresenter(BaseActivity baseActivity, GDTImgAds gdtImgAds, LinkedList<NativeExpressADView> gdtList
            , TaLeftTitleRightImgAds taLeftTitleRightImgAds
            , LinkedList<NativeAdResponse> adhubNativeData, NativeAd nativeAd) {
        this.baseActivity = baseActivity;
        mGdtAdLists = gdtList;
        mTaLeftTitleRightImgAds = taLeftTitleRightImgAds;
        mAdhubNativeData = adhubNativeData;
        mNativeAd = nativeAd;
//        loadAdhubAd();
//        loadAdhubAd();
    }

    private void loadGdtAd() {
        GDTImgAds mGdtImgAds = new GDTImgAds(new GDTImgAds.OnAdReceived() {
            @Override
            public void onGdtImgAdReceived(List<NativeExpressADView> mImgAds) {
                if (mImgAds != null) {
                    mGdtAdLists.clear();
                    mGdtAdLists.addAll(mImgAds);
                }
            }
        }, baseActivity);
        mGdtImgAds.load();
    }

    private void loadAdhubAd() {
        mNativeAd = new NativeAd(getContext(),ConstantAd.ADHUBAD.NATIVE_ID, 1, new NativeAdListener() {
            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                mAdhubNativeData.add(nativeAdResponse);
            }

            @Override
            public void onAdFailed(int i) {
                LogUtil.e("info","adhub load add fail "+i);
//                throw new IllegalStateException("adhub load add fail "+i);
            }
        });
        mNativeAd.loadAd();
    }

    /**
     * 这里我们要做的一个操作就是过滤并填充数据的一个操作；
     *
     * @param list
     */
    public List<MultiItemEntity> filterData(List<ResNewsAndVideoBean.HomeInfoBean> list
            , LinkedList<TTFeedAd> feedAdList) {
        mFeedAdList = feedAdList;

        ArrayList<MultiItemEntity> multiItemEntities = new ArrayList<>();

        if (list != null) {
            for (ResNewsAndVideoBean.HomeInfoBean homeInfoBean : list) {

                if (homeInfoBean.isNews()) {
                    //新闻的逻辑
                    multiItemEntities.add(getNews(homeInfoBean));
                    continue;
                }

                if (homeInfoBean.isVideo()) {
                    multiItemEntities.add(getVideo(homeInfoBean));
                    continue;
                }

                /*if(homeInfoBean.isAd()) {
                    MultiItemEntity entity = getAdhubNativeAd(homeInfoBean);
                    if(entity != null) {
                        multiItemEntities.add(entity);
                    } else {
                        LogUtil.e("info","getAdhubNativeAd is null");
                    }
                }*/

                if (homeInfoBean.isAd()) {
                    //这里暂时不做任何处理的操作；
                    MultiItemEntity entity = getAd(homeInfoBean);
                    if (entity != null) {
                        multiItemEntities.add(entity);
                    }
//                    continue;
                }
            }
        }
        return multiItemEntities;
    }

    private MultiItemEntity getVideo(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {
        //这里表示是视频的逻辑
        MultiItemEntity multiItemEntity = null;


        String title = homeInfoBean.getTitle();
        String source = homeInfoBean.getSource();
        String urlMd5 = homeInfoBean.getUrlmd5();
        String imageUrl = "";
        String item_id = homeInfoBean.getItem_id();
        String group_id = homeInfoBean.getGroup_id();
        String video_id = homeInfoBean.getVideo_id();
        String publishTime = homeInfoBean.getPublish_time();
        Long duration = homeInfoBean.getVideo_duration();

        try {
            imageUrl = homeInfoBean.getLarge_image_list().get(0).getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }

        multiItemEntity = new VideoListBean(source, urlMd5, title, imageUrl, item_id, group_id, video_id, publishTime, duration);

        return multiItemEntity;
    }

    private MultiItemEntity getNews(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {

        String topic = homeInfoBean.getTopic();
        String source = homeInfoBean.getSource();
        String date = homeInfoBean.getDate();
        String urlMd5 = homeInfoBean.getUrlmd5();
        boolean isWebContent = homeInfoBean.isWebContent();
        String url = homeInfoBean.getUrl();

        String newsType = homeInfoBean.getNew_type();
        int pageNum = homeInfoBean.getPageNum();

        MultiItemEntity itemEntity = null;

        if (homeInfoBean.isMuiltImgNews()) {

            //这里表示是单图的新闻；
            List<String> imageUrl = new ArrayList<>();

            try {
                if (homeInfoBean.getMiniimg() != null && homeInfoBean.getMiniimg().size() > 0) {

                    for (ResNewsAndVideoBean.MiniimgBean miniimgBean : homeInfoBean.getMiniimg()) {
                        imageUrl.add(miniimgBean.getSrc());
                    }
                    if (imageUrl.size()<3) {
                        for (int i=0;i<3-imageUrl.size();i++){
                            //这里会出现图小于3个的吗？
                            // TODO: 2018/4/11
                            imageUrl.add(homeInfoBean.getMiniimg().get(0).getSrc());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            itemEntity = new NewsThreeImageBean(imageUrl, topic, source, date, url, urlMd5, isWebContent , newsType , pageNum);

        } else {

            //这里表示是多图的新闻；
            String imageUrl = null;
            try {
                imageUrl = homeInfoBean.getMiniimg().get(0).getSrc();
            } catch (Exception e) {
                e.printStackTrace();
            }

            itemEntity = new NewsOneImageBean(imageUrl, topic, source, date, url, urlMd5, isWebContent , newsType , pageNum);
        }
        return itemEntity;
    }

    private MultiItemEntity getAd(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {
        Log.i("info" , "addType=========="+homeInfoBean.getType());
        MultiItemEntity multiItemEntity = null;
//        if (homeInfoBean.isTaAd()) {
//            multiItemEntity = getTuiAAd(homeInfoBean);
//        } else if (homeInfoBean.isCustomAd()) {
//            multiItemEntity = getCustomAd(homeInfoBean);
//        } else if (homeInfoBean.isCST()) {
//            multiItemEntity = getCstAd(homeInfoBean);
////            multiItemEntity = getBaiDuAd(homeInfoBean);
//        } else if (homeInfoBean.isBaiDu()) {
//            multiItemEntity = getBaiDuAd(homeInfoBean);
//        } else {
//            // if (homeInfoBean.isGdtAd())
            multiItemEntity = getGdtAd(homeInfoBean);
//        }

        // homeInfoBean
        return multiItemEntity;
    }

    /**
     * 获取自定义的广告；
     *
     * @return
     */
    private MultiItemEntity getCustomAd(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {

        MultiItemEntity multiItemEntity = null;

        String id=homeInfoBean.getId();
        String cont=homeInfoBean.getCont();
        String imgurl=homeInfoBean.getImgurl();
        String url=homeInfoBean.getUrl();
        String downurl=homeInfoBean.getDownurl();
        Long size=homeInfoBean.getSize();
        String packename=homeInfoBean.getPackename();
        String appname=homeInfoBean.getAppname();
        List<String> imgurls=homeInfoBean.getImgurls();
        String title=homeInfoBean.getTitle();

        if (homeInfoBean.isCustomBigBannerAd()) {
            multiItemEntity=new CustomBigBannerBean(downurl,url,size,packename,appname,imgurl);
        } else if (homeInfoBean.isCustomUpTitleDownImg()) {
            //上文下图；
            multiItemEntity=new CustomUpTitleDownImgBean(downurl,url,size,packename,appname,title,cont,imgurl);
        } else if (homeInfoBean.isCustomLeftTitleRightImg()) {
            multiItemEntity=new CustomLeftTitlRightImgBean(downurl,url,size,packename,appname,cont,title,imgurl);
        } else if (homeInfoBean.isCustomBanner20_3Ad()) {
            multiItemEntity=new CustomBanner20_3Bean(downurl,url,size,packename,appname,imgurl);
        } else {
            // if (homeInfoBean.isUpTitleDownMuiltyImg())
            multiItemEntity=new CustomTitleDownThreeImgBean(downurl,url,size,packename,appname,title,imgurls,cont);
        }

        return multiItemEntity;
    }

    /**
     * 获取推啊的广告；
     *
     * @return
     */
    private MultiItemEntity getTuiAAd(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {

        MultiItemEntity multiItemEntity=null;

        View view = mTaLeftTitleRightImgAds.getView();
        if (view != null) {
            TaLeftTitleRightImgBean taLeftTitleRightImgBean = new TaLeftTitleRightImgBean(view);
            taLeftTitleRightImgBean.setAdid(homeInfoBean.getAdid());
//            multiItemEntity=new TaLeftTitleRightImgBean(view);
            multiItemEntity = taLeftTitleRightImgBean;
        }
        return multiItemEntity;
    }

    /**
     * 这里是获取广点通的广告
     *
     * @return
     */
    private MultiItemEntity getGdtAd(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {

        MultiItemEntity multiItemEntity=null;

        if (mGdtAdLists!=null && mGdtAdLists.size()>0) {
            NativeExpressADView gdtadView = mGdtAdLists.removeFirst();

            gdtadView.setPadding(UIUtil.dip2px(getContext(), 12), UIUtil.dip2px(getContext(), 12),
                    UIUtil.dip2px(getContext(), 10), UIUtil.dip2px(getContext(), 10));

//            multiItemEntity = new GdtBigBannerBean(gdtadView);
            GdtBigBannerBean gdtBigBannerBean = new GdtBigBannerBean(gdtadView);
            gdtBigBannerBean.setAdid(homeInfoBean.getAdid());
            multiItemEntity = gdtBigBannerBean;
            if(mGdtAdLists.size()<2){
                loadGdtAd();
            }
        }else{
            loadGdtAd();
        }
        return multiItemEntity;
    }

    private MultiItemEntity getBaiDuAd(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {
        MultiItemEntity multiItemEntity = null;

        BaiDuBannerBean baiDuBannerBean = new BaiDuBannerBean();
        baiDuBannerBean.setCode(homeInfoBean.getId());
        baiDuBannerBean.setAdid(homeInfoBean.getAdid());
        multiItemEntity = baiDuBannerBean;

        return multiItemEntity;
    }

    /**
     * 这里是获取穿山甲的广告
     *
     * @return
     */
    private MultiItemEntity getCstAd(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {

        MultiItemEntity multiItemEntity = null;

        if(mFeedAdList != null && mFeedAdList.size()>0) {
            TTFeedAd ttFeedAd = mFeedAdList.removeFirst();

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
                smallPicAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = smallPicAd;
            } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
                TTFeedLargePicAd largePicAd = new TTFeedLargePicAd(title , des , source , icon
                        , imageList , interactionType , imageMode , view);
                largePicAd.setTtFeedAd(ttFeedAd);
                largePicAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = largePicAd;
            } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
                TTFeedGroupPicAd groupPicAd = new TTFeedGroupPicAd(title , des , source , icon
                        , imageList , interactionType , imageMode , view);
                groupPicAd.setTtFeedAd(ttFeedAd);
                groupPicAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = groupPicAd;
            } else if (ttFeedAd.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
                TTFeedVideoAd videoAd = new TTFeedVideoAd(title , des , source , icon
                        , imageList , interactionType , imageMode , view);
                videoAd.setTtFeedAd(ttFeedAd);
                videoAd.setAdid(homeInfoBean.getAdid());
                multiItemEntity = videoAd;
            }
        }

        return multiItemEntity;
    }

    private MultiItemEntity getAdhubNativeAd(ResNewsAndVideoBean.HomeInfoBean homeInfoBean) {
        MultiItemEntity multiItemEntity = null;
        if(mAdhubNativeData != null && mAdhubNativeData.size() > 0) {
            NativeAdResponse response = mAdhubNativeData.removeFirst();
            AdhubNativeBean bean = new AdhubNativeBean();

            bean.setResponse(response);
            bean.setNativeAd(mNativeAd);
            multiItemEntity = bean;
            if(mAdhubNativeData.size() < 2) {
                loadAdhubAd();
                loadAdhubAd();
            }
        } else {
            loadAdhubAd();
        }
        return multiItemEntity;
    }

    public void destory(){
//        if (mGdtImgAds != null) {
//            mGdtImgAds.destory();
//        }

        if (mTaLeftTitleRightImgAds != null) {
            mTaLeftTitleRightImgAds.destory();
        }
        mGdtAdLists=null;
    }

    public Context getContext(){
        return MyApplication.mContext;
    }

}
