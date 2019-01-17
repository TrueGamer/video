package com.huanxi.renrentoutiao.ui.media.hoder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.callback.AQuery2;
import com.androidquery.callback.ImageOptions;
import com.bytedance.sdk.openadsdk.DownloadStatusController;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.media.TTFeedVideoAd;
import com.huanxi.renrentoutiao.utils.TToast;
import com.huanxi.renrentoutiao.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class VideoAdViewHolder extends BaseMuiltyViewHolder<TTFeedVideoAd> {

    private Map<String , TTAppDownloadListener> mTTAppDownloadListenerMap = new WeakHashMap<>();

    @Override
    public void init(final TTFeedVideoAd ad, BaseViewHolder helper, final Context context) {

        RelativeLayout rlADLargeVideo = (RelativeLayout) helper.getView(R.id.rlADLargeVideo);
        TextView mTitle = (TextView) helper.getView(R.id.tv_listitem_ad_title);
        TextView mDescription = (TextView) helper.getView(R.id.tv_listitem_ad_desc);
        TextView mSource = (TextView)  helper.getView(R.id.tv_listitem_ad_source);
        FrameLayout videoView = (FrameLayout)  helper.getView(R.id.iv_listitem_video);
        ImageView mIcon = (ImageView)  helper.getView(R.id.iv_listitem_icon);
        Button mCreativeButton = (Button) helper.getView(R.id.btn_listitem_creative);
        Button mStopButton = (Button) helper.getView(R.id.btn_listitem_stop);
        Button mRemoveButton = (Button) helper.getView(R.id.btn_listitem_remove);

        AQuery2 mAQuery = new AQuery2(context);
        final TTFeedAd ttFeedAd = ad.getTtFeedAd();

        ttFeedAd.setVideoAdListener(new TTFeedAd.VideoAdListener() {
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

        if (videoView != null) {
            View video = ad.getAdView();
            if (video != null) {
                if (video.getParent() == null) {
                    videoView.removeAllViews();
                    videoView.addView(video);
                }
            }
        }

        //可以被点击的view, 也可以把convertView放进来意味item可被点击
        List<View> clickViewList = new ArrayList<View>();
        clickViewList.add(rlADLargeVideo);
        //触发创意广告的view（点击下载或拨打电话）
        List<View> creativeViewList = new ArrayList<View>();
        creativeViewList.add(mCreativeButton);
        //如果需要点击图文区域也能进行下载或者拨打电话动作，请将图文区域的view传入
//            creativeViewList.add(convertView);
        //重要! 这个涉及到广告计费，必须正确调用。convertView必须使用ViewGroup。
        ttFeedAd.registerViewForInteraction((ViewGroup) rlADLargeVideo, clickViewList, creativeViewList,
                new TTNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {

                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {

                    }

                    @Override
                    public void onAdShow(TTNativeAd ttNativeAd) {

                    }
                });
        mTitle.setText(ad.getTitle());
        mDescription.setText(ad.getDescription());
        mSource.setText(ad.getSource() == null ? "广告来源" : ad.getSource());
        TTImage icon = ad.getIcon();
        if (icon != null && icon.isValid()) {
            ImageOptions options = new ImageOptions();
            mAQuery.id(mIcon).image(icon.getImageUrl(), options);
        }
        Button adCreativeButton = mCreativeButton;
        switch (ad.getInteractionType()) {
            case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
                //如果初始化ttAdManager.createAdNative(getApplicationContext())没有传入activity 则需要在此传activity，否则影响使用Dislike逻辑
                if (context instanceof Activity) {
                    ttFeedAd.setActivityForDownloadApp((Activity) context);
                }
                adCreativeButton.setVisibility(View.VISIBLE);
                mStopButton.setVisibility(View.VISIBLE);
                mRemoveButton.setVisibility(View.VISIBLE);
                bindDownloadListener(adCreativeButton, mStopButton ,mRemoveButton, ttFeedAd);
                //绑定下载状态控制器
                bindDownLoadStatusController(mStopButton ,mRemoveButton , ttFeedAd , context);
                break;
            case TTAdConstant.INTERACTION_TYPE_DIAL:
                adCreativeButton.setVisibility(View.VISIBLE);
                adCreativeButton.setText("立即拨打");
                mStopButton.setVisibility(View.GONE);
                mRemoveButton.setVisibility(View.GONE);
                break;
            case TTAdConstant.INTERACTION_TYPE_LANDING_PAGE:
            case TTAdConstant.INTERACTION_TYPE_BROWSER:
//                    adCreativeButton.setVisibility(View.GONE);
                adCreativeButton.setVisibility(View.VISIBLE);
                adCreativeButton.setText("查看详情");
                mStopButton.setVisibility(View.GONE);
                mRemoveButton.setVisibility(View.GONE);
                break;
            default:
                adCreativeButton.setVisibility(View.GONE);
                mStopButton.setVisibility(View.GONE);
                mRemoveButton.setVisibility(View.GONE);
                TToast.show(context, "交互类型异常");
        }

    }

    private void bindDownLoadStatusController(Button mStopButton , Button mRemoveButton
            , final TTFeedAd ad , Context context) {
        final DownloadStatusController controller = ad.getDownloadStatusController();
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller != null) {
                    controller.changeDownloadStatus();
                    Log.d("info", "改变下载状态");
                }
            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller != null) {
                    controller.cancelDownload();
                    Log.d("info", "取消下载");
                }
            }
        });
    }

    private void bindDownloadListener(final Button adCreativeButton ,final Button mStopButton , Button mRemoveButton , TTFeedAd ad) {
        TTAppDownloadListener downloadListener = new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("开始下载");
                mStopButton.setText("开始下载");
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
                mStopButton.setText("下载中");
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("下载暂停 percent: " + (currBytes * 100 / totalBytes));
                mStopButton.setText("下载暂停");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("重新下载");
                mStopButton.setText("重新下载");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("点击打开");
                mStopButton.setText("点击打开");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                if (!isValid()) {
                    return;
                }
                adCreativeButton.setText("点击安装");
                mStopButton.setText("点击安装");
            }

            private boolean isValid() {
                return mTTAppDownloadListenerMap.get("VideoAdViewHolder") == this;
            }
        };
        //一个ViewHolder对应一个downloadListener, isValid判断当前ViewHolder绑定的listener是不是自己
        ad.setDownloadListener(downloadListener); // 注册下载监听器
        mTTAppDownloadListenerMap.put("VideoAdViewHolder", downloadListener);
    }

}
