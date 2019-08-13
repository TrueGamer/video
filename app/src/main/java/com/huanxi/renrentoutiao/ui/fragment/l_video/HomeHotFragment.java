package com.huanxi.renrentoutiao.ui.fragment.l_video;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTDrawFeedAd;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.globle.Constants;
import com.huanxi.renrentoutiao.interfaces.OnItemClickListener;
import com.huanxi.renrentoutiao.model.bean.l_video.VideoBean;
import com.huanxi.renrentoutiao.net.http.HttpCallback;
import com.huanxi.renrentoutiao.net.http.HttpUtil;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GDTImgAds;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta.TaUpTitleDownImgAds;
import com.huanxi.renrentoutiao.ui.media.TTFeedGroupPicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedLargePicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedSmallPicAd;
import com.huanxi.renrentoutiao.ui.media.TTFeedVideoAd;
import com.huanxi.renrentoutiao.ui.view.video.RefreshAdapter;
import com.huanxi.renrentoutiao.ui.view.video.RefreshView;
import com.huanxi.renrentoutiao.utils.TTAdManagerHolder;
import com.huanxi.renrentoutiao.utils.VideoStorge;
import com.huanxi.renrentoutiao.ui.view.video.ItemDecoration;
import com.huanxi.renrentoutiao.ui.activity.video.VideoPlayActivity;
import com.huanxi.renrentoutiao.ui.adapter.l_video.FollowVideoAdapter;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cxf on 2018/6/9.
 */

public class HomeHotFragment extends AbsFragment implements OnItemClickListener<VideoBean> {

    private RefreshView mRefreshView;
    private FollowVideoAdapter mFollowAdapter;
    private boolean mFirst = true;
    private TTAdNative mTTAdNative; // 网盟广告
    private LinkedList<TTDrawFeedAd> mData; // 网盟广告列表

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_hot;
    }

    @Override
    protected void main() {

        mData = new LinkedList<>();
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance(getActivity());
        mTTAdNative = ttAdManager.createAdNative(getActivity());
//        loadListAd();

        mRefreshView = (RefreshView) mRootView.findViewById(R.id.refreshView);
        mRefreshView.setNoDataLayoutId(R.layout.view_no_data_default);
        mRefreshView.setDataHelper(new RefreshView.DataHelper<VideoBean>() {

            @Override
            public RefreshAdapter<VideoBean> getAdapter() {
                if (mFollowAdapter == null) {
                    mFollowAdapter = new FollowVideoAdapter(mContext);
                    mFollowAdapter.setOnItemClickListener(HomeHotFragment.this);
                }
                return mFollowAdapter;
            }

            @Override
            public void loadData(int p, HttpCallback callback) {
                HttpUtil.getVideoList(p, callback);
            }

            @Override
            public List<VideoBean> processData(String[] info) {
                return JSON.parseArray(Arrays.toString(info), VideoBean.class);
            }

            @Override
            public void onRefresh(List<VideoBean> list) {
                VideoStorge.getInstance().put(Constants.VIDEO_HOT, list);
            }
            @Override
            public void onNoData(boolean noData) {

            }

            @Override
            public void onLoadDataCompleted(int dataCount) {

            }

        });
        mRefreshView.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 2, 2);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        mRefreshView.setItemDecoration(decoration);

        refresh();
    }

    public void loadListAd() {
        //这里初始化广点通广告的逻辑
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("926821115") //开发者申请的广告位
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920) //符合广告场景的广告尺寸
                .setAdCount(1) //请求广告数量为1到3条
                .build();
        //加载广告
        mTTAdNative.loadDrawFeedAd(adSlot, new TTAdNative.DrawFeedAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d("error", message);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
                Toast.makeText(mContext, "有广告", Toast.LENGTH_SHORT).show();
                if (ads == null || ads.isEmpty()) {
                    return;
                }

            }
        });
    }

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

    @Override
    public void onItemClick(VideoBean bean, int position) {
        if (mRefreshView != null && bean != null && bean.getUserinfo() != null) {
            VideoPlayActivity.forwardVideoPlay(mContext, Constants.VIDEO_HOT, position, mRefreshView.getPage(), bean.getUserinfo(),bean.getIsattent());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mFirst) {
                mFirst = false;
                mRefreshView.initData();
            }
        }
    }

    @Override
    public void onDestroyView() {
        HttpUtil.cancel(HttpUtil.GET_VIDEO_LIST);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if(mRefreshView!=null){
            mRefreshView.setDataHelper(null);
        }
        super.onDestroy();
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        if(mRefreshView != null) {
            mRefreshView.initData();
        }
    }
}
