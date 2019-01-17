package com.huanxi.renrentoutiao.ui.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GdtNativeAds;
import com.huanxi.renrentoutiao.presenter.ads.ta.TaNativeAds;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.activity.video.VideoItemDetailActivity;
import com.huanxi.renrentoutiao.ui.adapter.bean.VideoBean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.video.VideoListBean;
import com.huanxi.renrentoutiao.utils.FormatUtils;
import com.qq.e.ads.nativ.NativeExpressADView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/2/6.
 */

public class VideoListAdapter extends BaseAdsAdapter {


    public static final int VIDEO_ITEM = 2;

    private final Activity mActivity;

    public HashMap<Integer,View> mAds=new HashMap<>();

    public LinkedList<View> mTaViews;


    public VideoListAdapter(Activity activity, @Nullable List<VideoBean> data,LinkedList<View> taViews) {
        super(data);
        addItemType(VIDEO_ITEM, R.layout.item_new_vedio_layout);
        mActivity = activity;
        mTaViews=taViews;
    }


    @Override
    protected void convert(BaseViewHolder helper, final MultiItemEntity item) {
        super.convert(helper, item);
        if (item instanceof VideoBean) {

            switch (item.getItemType()) {
                case VIDEO_ITEM:

                    final VideoBean videoItem = (VideoBean) item;
                    try {
                        helper.setText(R.id.tv_news_title, videoItem.getContent().getTitle());

                        helper.setText(R.id.tv_duration, FormatUtils.formatSecondToTime(videoItem.getContent().getVideo_duration()));

                        helper.setText(R.id.tv_source,videoItem.getContent().getSource());
                        helper.getView(R.id.tv_time).setVisibility(View.INVISIBLE);
                        Glide.with(mActivity).load(videoItem.getContent().getLarge_image_list().get(0).getUrl()).into(((ImageView) helper.getView(R.id.iv_image)));

                        helper.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              //  mActivity.startActivity(VideoItemDetailActivity.getIntent(mActivity, videoItem));
                                //这里我们要用presenter将其转换为对应的对象；
                                mActivity.startActivity(VideoItemDetailActivity.getIntent(mActivity, ((VideoListBean) getVideo(videoItem))));

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case BaseAdsAdapter.GDT_UP_TEXT_DOWN_IMG:
                    //上文下图的操作；
                    //mGDTUpTextDownImgPresenter.init(helper.itemView);
                    doAddGDTView(helper);
                    break;
                case BaseAdsAdapter.TA_UP_TEXT_DOWN_IMG:
                    initTaUpText(helper);
                    break;
                case BaseAdsAdapter.CUSTOMER_BANNNER_AD_1:
                    //纯图
                    mCustomerBannerAd.init(helper, ((VideoBean) item).getAdBean(),mContext);
                    break;
                case BaseAdsAdapter.CUSTOMER_UP_TITLE_DOWN_IMG_AD_2:
                    //上文右图
                    mCustomerTitleAndImgAd.init(helper,  ((VideoBean) item).getAdBean(),mContext);
                    break;
                case BaseAdsAdapter.CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8:
                    //这里左文右图

                    mCustomLeftTitleRightImgAd.init(helper, ((VideoBean) item).getAdBean(),mContext);
                    break;
                case BaseAdsAdapter.CUSTOMER_BANNER_20_3:

                    mCustomBanner20_3.init(helper.itemView,((VideoBean) item).getAdBean(),mContext);
                    break;
                case BaseAdsAdapter.CUSTOMER_UP_TITLE_DOWN_MUILT_IMG:

                    mCustomUpTitleMuiltyImg.init(helper.itemView,((VideoBean) item).getAdBean(),mContext);
                    break;
            }

        }
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
            imageUrl = videoBean.getContent().getLarge_image_list().get(0).getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }

        multiItemEntity = new VideoListBean(source, urlMd5, title, imageUrl, item_id, group_id, video_id, publishTime, duration);

        return multiItemEntity;
    }


    /*
    这里要做的一个操作就是添加广点通的广告；
    */
    private void doAddGDTView(BaseViewHolder helper) {

        GdtNativeAds gdtNativeAds = GdtNativeAds.newInstance();

        NativeExpressADView gdtadView = gdtNativeAds.getAdView();

        if (gdtadView != null) {
            ViewGroup itemView = (ViewGroup) helper.itemView;

            gdtadView.setPadding(0, 0,
                    0, UIUtil.dip2px(mContext, 12));

            if (itemView.getChildCount()>0) {

                View view = itemView.getChildAt(0);
                //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
                if (view instanceof NativeExpressADView) {
                    gdtNativeAds.removeADView(((NativeExpressADView) view));
                }

                itemView.removeAllViews();
            }

            itemView.addView(gdtadView);
            gdtadView.render();
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_line, itemView, false);
            itemView.addView(view, 1);
        }
    }

    private void initTaLeftText(BaseViewHolder helper) {

        TaNativeAds taNativeAds = TaNativeAds.newInstance();

        View leftTextAdView = taNativeAds.getLeftText();


        ViewGroup itemView = (ViewGroup) helper.itemView;

        if (itemView.getChildCount()>0) {

            View view = itemView.getChildAt(0);
            //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
            if (view instanceof LinearLayout) {
                taNativeAds.releaseLeftText( view);
            }

            itemView.removeAllViews();
        }

        itemView.addView(leftTextAdView);
    }

    private void initTaUpText(BaseViewHolder helper) {

        TaNativeAds taNativeAds = TaNativeAds.newInstance();

        View leftTextAdView = taNativeAds.getUpText();

        ViewGroup itemView = (ViewGroup) helper.itemView;

        if (itemView.getChildCount()>0) {

            View view = itemView.getChildAt(0);
            //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
            if (view instanceof LinearLayout) {
                taNativeAds.releaseUpText(view);
            }

            itemView.removeAllViews();
        }
        itemView.addView(leftTextAdView);
    }

}
