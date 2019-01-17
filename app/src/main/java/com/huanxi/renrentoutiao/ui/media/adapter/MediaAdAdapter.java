package com.huanxi.renrentoutiao.ui.media.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.callback.AQuery2;
import com.androidquery.callback.ImageOptions;
import com.bytedance.sdk.openadsdk.DownloadStatusController;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.TToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 网盟广告Adapter
 */
public class MediaAdAdapter extends BaseAdapter {

    private static final int ITEM_VIEW_TYPE_NORMAL = 0;
    private static final int ITEM_VIEW_TYPE_GROUP_PIC_AD = 1;
    private static final int ITEM_VIEW_TYPE_SMALL_PIC_AD = 2;
    private static final int ITEM_VIEW_TYPE_LARGE_PIC_AD = 3;
    private static final int ITEM_VIEW_TYPE_VIDEO = 4;

    private int mVideoCount = 0;

    private List<TTFeedAd> mData;
    private Context mContext;
    private AQuery2 mAQuery;//AQuery2可以播放gif。 也可以使用自己的图片框架来加载图片。

    private Map<AdViewHolder, TTAppDownloadListener> mTTAppDownloadListenerMap = new WeakHashMap<>();

    public MediaAdAdapter(Context context, List<TTFeedAd> data) {
        this.mContext = context;
        this.mData = data;
        this.mAQuery = new AQuery2(context);
    }

    @Override
    public int getCount() {
        return mData.size(); // for test
    }

    @Override
    public TTFeedAd getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        TTFeedAd ad = getItem(position);
        if (ad == null) {
            return ITEM_VIEW_TYPE_NORMAL;
        } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
            return ITEM_VIEW_TYPE_SMALL_PIC_AD;
        } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
            return ITEM_VIEW_TYPE_LARGE_PIC_AD;
        } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
            return ITEM_VIEW_TYPE_GROUP_PIC_AD;
        } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
            return ITEM_VIEW_TYPE_VIDEO;
        } else {
            TToast.show(mContext, "图片展示样式错误");
            return ITEM_VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TTFeedAd ad = getItem(position);
        switch (getItemViewType(position)) {
            case ITEM_VIEW_TYPE_SMALL_PIC_AD:
                return getSmallAdView(convertView, parent, ad);
            case ITEM_VIEW_TYPE_LARGE_PIC_AD:
                return getLargeAdView(convertView, parent, ad);
            case ITEM_VIEW_TYPE_GROUP_PIC_AD:
                return getGroupAdView(convertView, parent, ad);
            case ITEM_VIEW_TYPE_VIDEO:
                return getVideoView(convertView, parent, ad);
            default:
                return getNormalView(convertView, parent, position);
        }
    }

    private View getVideoView(View convertView, ViewGroup parent, @NonNull final TTFeedAd ad) {
        final VideoAdViewHolder adViewHolder;
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_large_video, parent, false);
                adViewHolder = new VideoAdViewHolder();
                adViewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_listitem_ad_title);
                adViewHolder.mDescription = (TextView) convertView.findViewById(R.id.tv_listitem_ad_desc);
                adViewHolder.mSource = (TextView) convertView.findViewById(R.id.tv_listitem_ad_source);
                adViewHolder.videoView = (FrameLayout) convertView.findViewById(R.id.iv_listitem_video);
                adViewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_listitem_icon);
                adViewHolder.mCreativeButton = (Button) convertView.findViewById(R.id.btn_listitem_creative);
                adViewHolder.mStopButton = (Button) convertView.findViewById(R.id.btn_listitem_stop);
                adViewHolder.mRemoveButton = (Button) convertView.findViewById(R.id.btn_listitem_remove);
                convertView.setTag(adViewHolder);
            } else {
                adViewHolder = (VideoAdViewHolder) convertView.getTag();
            }

            ad.setVideoAdListener(new TTFeedAd.VideoAdListener() {
                @Override
                public void onVideoLoad(TTFeedAd ad) {

                }

                @Override
                public void onVideoError(int errorCode, int extraCode) {

                }

                @Override
                public void onVideoAdStartPlay(TTFeedAd ad) {

                }

                @Override
                public void onVideoAdPaused(TTFeedAd ad) {

                }

                @Override
                public void onVideoAdContinuePlay(TTFeedAd ad) {

                }
            });

            bindData(convertView, adViewHolder, ad);
            if (ad != null && adViewHolder.videoView != null) {
                View video = ad.getAdView();
                if (video != null) {
                    if (video.getParent() == null) {
                        adViewHolder.videoView.removeAllViews();
                        adViewHolder.videoView.addView(video);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private View getLargeAdView(View convertView, ViewGroup parent, @NonNull final TTFeedAd ad) {
        final LargeAdViewHolder adViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_large_pic, parent, false);
            adViewHolder = new LargeAdViewHolder();
            adViewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_listitem_ad_title);
            adViewHolder.mDescription = (TextView) convertView.findViewById(R.id.tv_listitem_ad_desc);
            adViewHolder.mSource = (TextView) convertView.findViewById(R.id.tv_listitem_ad_source);
            adViewHolder.mLargeImage = (ImageView) convertView.findViewById(R.id.iv_listitem_image);
            adViewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_listitem_icon);
            adViewHolder.mCreativeButton = (Button) convertView.findViewById(R.id.btn_listitem_creative);
            adViewHolder.mStopButton = (Button) convertView.findViewById(R.id.btn_listitem_stop);
            adViewHolder.mRemoveButton = (Button) convertView.findViewById(R.id.btn_listitem_remove);
            convertView.setTag(adViewHolder);
        } else {
            adViewHolder = (LargeAdViewHolder) convertView.getTag();
        }
        bindData(convertView, adViewHolder, ad);
        if (ad.getImageList() != null && !ad.getImageList().isEmpty()) {
            TTImage image = ad.getImageList().get(0);
            if (image != null && image.isValid()) {
                mAQuery.id(adViewHolder.mLargeImage).image(image.getImageUrl());
            }
        }
        return convertView;
    }

    private View getGroupAdView(View convertView, ViewGroup parent, @NonNull final TTFeedAd ad) {
        GroupAdViewHolder adViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_group_pic, parent, false);
            adViewHolder = new GroupAdViewHolder();
            adViewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_listitem_ad_title);
            adViewHolder.mSource = (TextView) convertView.findViewById(R.id.tv_listitem_ad_source);
            adViewHolder.mDescription = (TextView) convertView.findViewById(R.id.tv_listitem_ad_desc);
            adViewHolder.mGroupImage1 = (ImageView) convertView.findViewById(R.id.iv_listitem_image1);
            adViewHolder.mGroupImage2 = (ImageView) convertView.findViewById(R.id.iv_listitem_image2);
            adViewHolder.mGroupImage3 = (ImageView) convertView.findViewById(R.id.iv_listitem_image3);
            adViewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_listitem_icon);
            adViewHolder.mCreativeButton = (Button) convertView.findViewById(R.id.btn_listitem_creative);
            adViewHolder.mStopButton = (Button) convertView.findViewById(R.id.btn_listitem_stop);
            adViewHolder.mRemoveButton = (Button) convertView.findViewById(R.id.btn_listitem_remove);
            convertView.setTag(adViewHolder);
        } else {
            adViewHolder = (GroupAdViewHolder) convertView.getTag();
        }
        bindData(convertView, adViewHolder, ad);
        if (ad.getImageList() != null && ad.getImageList().size() >= 3) {
            TTImage image1 = ad.getImageList().get(0);
            TTImage image2 = ad.getImageList().get(1);
            TTImage image3 = ad.getImageList().get(2);
            if (image1 != null && image1.isValid()) {
                mAQuery.id(adViewHolder.mGroupImage1).image(image1.getImageUrl());
            }
            if (image2 != null && image2.isValid()) {
                mAQuery.id(adViewHolder.mGroupImage2).image(image2.getImageUrl());
            }
            if (image3 != null && image3.isValid()) {
                mAQuery.id(adViewHolder.mGroupImage3).image(image3.getImageUrl());
            }
        }
        return convertView;
    }


    private View getSmallAdView(View convertView, ViewGroup parent, @NonNull final TTFeedAd ad) {
        SmallAdViewHolder adViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_small_pic, parent, false);
            adViewHolder = new SmallAdViewHolder();
            adViewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_listitem_ad_title);
            adViewHolder.mSource = (TextView) convertView.findViewById(R.id.tv_listitem_ad_source);
            adViewHolder.mDescription = (TextView) convertView.findViewById(R.id.tv_listitem_ad_desc);
            adViewHolder.mSmallImage = (ImageView) convertView.findViewById(R.id.iv_listitem_image);
            adViewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_listitem_icon);
            adViewHolder.mCreativeButton = (Button) convertView.findViewById(R.id.btn_listitem_creative);
            adViewHolder.mStopButton = (Button) convertView.findViewById(R.id.btn_listitem_stop);
            adViewHolder.mRemoveButton = (Button) convertView.findViewById(R.id.btn_listitem_remove);
            convertView.setTag(adViewHolder);
        } else {
            adViewHolder = (SmallAdViewHolder) convertView.getTag();
        }
        bindData(convertView, adViewHolder, ad);
        if (ad.getImageList() != null && !ad.getImageList().isEmpty()) {
            TTImage image = ad.getImageList().get(0);
            if (image != null && image.isValid()) {
                mAQuery.id(adViewHolder.mSmallImage).image(image.getImageUrl());
            }
        }

        //接入网盟的dislike 逻辑
//            final TTAdDislike ttAdDislike = ad.getDislikeDialog();
//            if (ttAdDislike != null) {
//                ttAdDislike.setDislikeInteractionCallback(new TTAdDislike.DislikeInteractionCallback() {
//                    @Override
//                    public void onSelected(int position, String value) {
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//                dislikeIcon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ttAdDislike.showDislikeDialog();
//                    }
//                });
//            }
        return convertView;
    }

    /**
     * 非广告list
     *
     * @param convertView
     * @param parent
     * @param position
     * @return
     */
    private View getNormalView(View convertView, ViewGroup parent, int position) {
        NormalViewHolder normalViewHolder;
        if (convertView == null) {
            normalViewHolder = new NormalViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_media_new_list_layout, parent, false);
            convertView.setTag(normalViewHolder);
        } else {
            normalViewHolder = (NormalViewHolder) convertView.getTag();
        }
        normalViewHolder.idle.setText("ListView item " + position);
        return convertView;
    }

    private void bindData(View convertView, final AdViewHolder adViewHolder, TTFeedAd ad) {
        //可以被点击的view, 也可以把convertView放进来意味item可被点击
        List<View> clickViewList = new ArrayList<View>();
        clickViewList.add(convertView);
        //触发创意广告的view（点击下载或拨打电话）
        List<View> creativeViewList = new ArrayList<View>();
        creativeViewList.add(adViewHolder.mCreativeButton);
        //如果需要点击图文区域也能进行下载或者拨打电话动作，请将图文区域的view传入
//            creativeViewList.add(convertView);
        //重要! 这个涉及到广告计费，必须正确调用。convertView必须使用ViewGroup。
        ad.registerViewForInteraction((ViewGroup) convertView, clickViewList, creativeViewList,
                new TTNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {
                        if (ttNativeAd != null) {
//                            TToast.show(mContext, "广告" + ad.getTitle() + "被点击");
                        }
                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
                        if (ttNativeAd != null) {
//                            TToast.show(mContext, "广告" + ad.getTitle() + "被创意按钮被点击");
                        }
                    }

                    @Override
                    public void onAdShow(TTNativeAd ttNativeAd) {
                        if (ttNativeAd != null) {
//                            TToast.show(mContext, "广告" + ad.getTitle() + "展示");
                        }
                    }
                });
        adViewHolder.mTitle.setText(ad.getTitle());
        adViewHolder.mDescription.setText(ad.getDescription());
        adViewHolder.mSource.setText(ad.getSource() == null ? "广告来源" : ad.getSource());
        TTImage icon = ad.getIcon();
        if (icon != null && icon.isValid()) {
            ImageOptions options = new ImageOptions();
            mAQuery.id(adViewHolder.mIcon).image(icon.getImageUrl(), options);
        }
        Button adCreativeButton = adViewHolder.mCreativeButton;
        switch (ad.getInteractionType()) {
            case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
                //如果初始化ttAdManager.createAdNative(getApplicationContext())没有传入activity 则需要在此传activity，否则影响使用Dislike逻辑
                if (mContext instanceof Activity) {
                    ad.setActivityForDownloadApp((Activity) mContext);
                }
                adCreativeButton.setVisibility(View.VISIBLE);
                adViewHolder.mStopButton.setVisibility(View.VISIBLE);
                adViewHolder.mRemoveButton.setVisibility(View.VISIBLE);
                bindDownloadListener(adCreativeButton, adViewHolder, ad);
                //绑定下载状态控制器
                bindDownLoadStatusController(adViewHolder, ad);
                break;
            case TTAdConstant.INTERACTION_TYPE_DIAL:
                adCreativeButton.setVisibility(View.VISIBLE);
                adCreativeButton.setText("立即拨打");
                adViewHolder.mStopButton.setVisibility(View.GONE);
                adViewHolder.mRemoveButton.setVisibility(View.GONE);
                break;
            case TTAdConstant.INTERACTION_TYPE_LANDING_PAGE:
            case TTAdConstant.INTERACTION_TYPE_BROWSER:
//                    adCreativeButton.setVisibility(View.GONE);
                adCreativeButton.setVisibility(View.VISIBLE);
                adCreativeButton.setText("查看详情");
                adViewHolder.mStopButton.setVisibility(View.GONE);
                adViewHolder.mRemoveButton.setVisibility(View.GONE);
                break;
            default:
                adCreativeButton.setVisibility(View.GONE);
                adViewHolder.mStopButton.setVisibility(View.GONE);
                adViewHolder.mRemoveButton.setVisibility(View.GONE);
                TToast.show(mContext, "交互类型异常");
        }
    }

    private void bindDownLoadStatusController(AdViewHolder adViewHolder, final TTFeedAd ad) {
        final DownloadStatusController controller = ad.getDownloadStatusController();
        adViewHolder.mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller != null) {
                    controller.changeDownloadStatus();
                    TToast.show(mContext, "改变下载状态");
                    Log.d("info", "改变下载状态");
                }
            }
        });

        adViewHolder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller != null) {
                    controller.cancelDownload();
                    TToast.show(mContext, "取消下载");
                    Log.d("info", "取消下载");
                }
            }
        });
    }

    private void bindDownloadListener(final Button adCreativeButton, final AdViewHolder adViewHolder, TTFeedAd ad) {
        TTAppDownloadListener downloadListener = new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("开始下载");
                adViewHolder.mStopButton.setText("开始下载");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                if (totalBytes < 0L) {
                    adCreativeButton.setText("下载中 percent: 0");
                } else {
                    adCreativeButton.setText("下载中 percent: " + (currBytes * 100 / totalBytes));
                }
                adViewHolder.mStopButton.setText("下载中");
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("下载暂停 percent: " + (currBytes * 100 / totalBytes));
                adViewHolder.mStopButton.setText("下载暂停");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("重新下载");
                adViewHolder.mStopButton.setText("重新下载");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("点击打开");
                adViewHolder.mStopButton.setText("点击打开");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("点击安装");
                adViewHolder.mStopButton.setText("点击安装");
            }

            private boolean isValid() {
                return mTTAppDownloadListenerMap.get(adViewHolder) == this;
            }
        };
        //一个ViewHolder对应一个downloadListener, isValid判断当前ViewHolder绑定的listener是不是自己
        ad.setDownloadListener(downloadListener); // 注册下载监听器
        mTTAppDownloadListenerMap.put(adViewHolder, downloadListener);
    }

    private static class VideoAdViewHolder extends AdViewHolder {
        FrameLayout videoView;
    }

    private static class LargeAdViewHolder extends AdViewHolder {
        ImageView mLargeImage;
    }

    private static class SmallAdViewHolder extends AdViewHolder {
        ImageView mSmallImage;
    }

    private static class GroupAdViewHolder extends AdViewHolder {
        ImageView mGroupImage1;
        ImageView mGroupImage2;
        ImageView mGroupImage3;
    }

    private static class AdViewHolder {
        ImageView mIcon;
        Button mCreativeButton;
        TextView mTitle;
        TextView mDescription;
        TextView mSource;
        Button mStopButton;
        Button mRemoveButton;
    }

    private static class NormalViewHolder {
        TextView idle;
    }
}
