package com.huanxi.renrentoutiao.ui.media;


import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.DownloadStatusController;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.BaseMuiltyAdapterBean;
import com.huanxi.renrentoutiao.ui.media.hoder.GroupAdViewHolder;
import com.huanxi.renrentoutiao.ui.media.hoder.LargeAdViewHolder;
import com.huanxi.renrentoutiao.ui.media.hoder.SmallAdViewHolder;

import java.util.List;

public class TTFeedGroupPicAd extends BaseMuiltyAdapterBean{

    private String title;
    private String description;
    private String source;
    private TTImage icon;
    private List<TTImage> imageList;
    private int interactionType;
    private int imageMode;
    private View adView;
    private long adid;

    public TTFeedGroupPicAd(String title, String description, String source, TTImage icon,
                            List<TTImage> imageList, int interactionType, int imageMode , View view) {
        this.title = title;
        this.description = description;
        this.source = source;
        this.icon = icon;
        this.imageList = imageList;
        this.interactionType = interactionType;
        this.imageMode = imageMode;
        this.adView = view;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public TTImage getIcon() {
        return icon;
    }

    public void setIcon(TTImage icon) {
        this.icon = icon;
    }

    public List<TTImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<TTImage> imageList) {
        this.imageList = imageList;
    }

    public int getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(int interactionType) {
        this.interactionType = interactionType;
    }

    public int getImageMode() {
        return imageMode;
    }

    public void setImageMode(int imageMode) {
        this.imageMode = imageMode;
    }

    @Override
    public int getItemType() {
        return GroupAdViewHolder.class.hashCode();
    }

    public View getAdView() {
        return adView;
    }

    public void setAdView(View adView) {
        this.adView = adView;
    }

    private TTFeedAd ttFeedAd;

    public TTFeedAd getTtFeedAd() {
        return ttFeedAd;
    }

    public void setTtFeedAd(TTFeedAd ttFeedAd) {
        this.ttFeedAd = ttFeedAd;
    }

    public long getAdid() {
        return adid;
    }

    public void setAdid(long adid) {
        this.adid = adid;
    }
}
