package com.huanxi.renrentoutiao.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.presenter.ads.customer.CustomBanner20_3;
import com.huanxi.renrentoutiao.presenter.ads.customer.CustomImageAd;
import com.huanxi.renrentoutiao.presenter.ads.customer.CustomLeftTitleRightImgAd;
import com.huanxi.renrentoutiao.presenter.ads.customer.CustomUpTitleMuiltyImg;
import com.huanxi.renrentoutiao.presenter.ads.customer.TitleAndImgAd;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GDTBannerPresenter;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GDTUpTextDownImgPresenter;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GdtNativeAds;
import com.huanxi.renrentoutiao.presenter.ads.ta.MyTMNaTmView;
import com.huanxi.renrentoutiao.presenter.ads.ta.TaBannerPresenter;
import com.huanxi.renrentoutiao.presenter.ads.ta.TaNativeAds;
import com.huanxi.renrentoutiao.presenter.ads.ta.TaUpTextDownImgPresernter;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.baidu.BaiDuAdHolder;
import com.huanxi.renrentoutiao.ui.media.hoder.LargeAdViewHolder;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;

import cn.tongdun.android.shell.db.utils.LogUtil;

/**
 * Created by Dinosa on 2018/2/10.
 * 广告的Adapter的一个基类
 */

public abstract class BaseAdsAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

    public static final int CUSTOMER_BANNNER_AD_1 = -1;
    public static final int CUSTOMER_UP_TITLE_DOWN_IMG_AD_2 = -2;
    public static final int CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8 = -8;
    public static final int CUSTOMER_BANNER_20_3 = -9;  //20比3
    public static final int CUSTOMER_UP_TITLE_DOWN_MUILT_IMG = -10;  //20比3

    public static final int GDT_BANNER = -3;

    public static final int GDT_UP_TEXT_DOWN_IMG = -4;

    public static final int TA_BANNER = -5;

    public static final int TA_LEFT_TEXT_RIGHT_IMG = -6; //左文，右图；
    public static final int TA_UP_TEXT_DOWN_IMG = -7; //上文，下图；

    public static final int CSJ_LARGE = -11; // 穿山甲
    public static final int BAIDU_AD = -12; // 百度

    protected final CustomImageAd mCustomerBannerAd;
    protected final TitleAndImgAd mCustomerTitleAndImgAd;
    protected final GDTBannerPresenter mGDTBannerPresenter;
    protected final GDTUpTextDownImgPresenter mGDTUpTextDownImgPresenter;
    protected final TaBannerPresenter mTaBannerPresenter;
    protected final TaUpTextDownImgPresernter mTaUpTextDownImgPresernter;
    protected final CustomLeftTitleRightImgAd mCustomLeftTitleRightImgAd;
    protected final CustomBanner20_3 mCustomBanner20_3;
    protected final CustomUpTitleMuiltyImg mCustomUpTitleMuiltyImg;

    protected final LargeAdViewHolder largeAdViewHolder;
    protected final BaiDuAdHolder baiDuAdHolder;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseAdsAdapter(List<T> data) {
        super(data);

        addItemType(CUSTOMER_BANNNER_AD_1, R.layout.item_customer_ad1);
        addItemType(CUSTOMER_UP_TITLE_DOWN_IMG_AD_2, R.layout.item_customer_ad2);
        addItemType(CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8, R.layout.item_customer_ad3);
        addItemType(CUSTOMER_BANNER_20_3, R.layout.item_customer_ad4);
        addItemType(CUSTOMER_UP_TITLE_DOWN_MUILT_IMG, R.layout.item_customer_ad5);

        addItemType(GDT_BANNER, R.layout.item_gdt_banner);
        addItemType(GDT_UP_TEXT_DOWN_IMG, R.layout.item_gdt_up_text_down_img);

        addItemType(TA_BANNER, R.layout.item_ta_banner);
        addItemType(TA_LEFT_TEXT_RIGHT_IMG, R.layout.item_ta_left_text_right_img);
        addItemType(TA_UP_TEXT_DOWN_IMG, R.layout.item_ta_up_text_down_img);

        addItemType(CSJ_LARGE , R.layout.listitem_ad_large_pic);

        addItemType(BAIDU_AD , R.layout.item_baidu_ad_layout);

        mCustomerBannerAd = new CustomImageAd();
        mCustomerTitleAndImgAd = new TitleAndImgAd();
        mCustomLeftTitleRightImgAd = new CustomLeftTitleRightImgAd();
        mGDTBannerPresenter = new GDTBannerPresenter();
        mGDTUpTextDownImgPresenter = new GDTUpTextDownImgPresenter();
        mTaBannerPresenter = new TaBannerPresenter();
        mTaUpTextDownImgPresernter = new TaUpTextDownImgPresernter();

        mCustomBanner20_3 = new CustomBanner20_3();
        mCustomUpTitleMuiltyImg = new CustomUpTitleMuiltyImg();

        largeAdViewHolder = new LargeAdViewHolder();
        baiDuAdHolder = new BaiDuAdHolder();

    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if(item instanceof AdBean){
            int itemType = item.getItemType();
            switch (itemType) {

                case CUSTOMER_BANNNER_AD_1:

                    mCustomerBannerAd.init(helper, ((AdBean) item),mContext);

                    break;

                case CUSTOMER_UP_TITLE_DOWN_IMG_AD_2:
                    mCustomerTitleAndImgAd.init(helper, ((AdBean) item),mContext);
                    break;

                case GDT_BANNER:

                    mGDTBannerPresenter.init(helper.itemView);

                    break;
                case GDT_UP_TEXT_DOWN_IMG:

                    doAddGDTView(helper);
//                    mGDTUpTextDownImgPresenter.init(helper.itemView);
                    break;
                case TA_LEFT_TEXT_RIGHT_IMG:
                    //推啊的左文右图；
                    initTaLeftTa(helper);
                    //doAddGDTView(helper);
                    //mTaBannerPresenter.init(helper.itemView);
                    break;
                case TA_UP_TEXT_DOWN_IMG:
                    initUpText(helper);
                    //mTaUpTextDownImgPresernter.init(helper.itemView);
                    break;
                case TA_BANNER:
                    //推啊banner;
                    mTaBannerPresenter.init(helper.itemView);
                    break;
                case CUSTOMER_LEFT_TITLE_RIGHT_IMG_AD_8:
                    //这里要做的一个操作就是显示左文右图：
                    mCustomLeftTitleRightImgAd.init(helper, ((AdBean) item),mContext);
                    break;
                case CUSTOMER_BANNER_20_3:
                    //这里我们要做的一个操作就是显示banner；
                    mCustomBanner20_3.init(helper.itemView, ((AdBean) item),mContext);
                    break;
                case CUSTOMER_UP_TITLE_DOWN_MUILT_IMG:
                    mCustomUpTitleMuiltyImg.init(helper.itemView, ((AdBean) item),mContext);
                    break;
                case CSJ_LARGE:
                    TTFeedAd ttFeedAd = ((AdBean) item).getTtFeedAd();
                    if(ttFeedAd != null) {
                        LogUtil.d(TAG,"ttfeedAd is not null");
                        largeAdViewHolder.init2(ttFeedAd , helper , mContext);
                    } else {
                        LogUtil.d(TAG,"ttfeedAd is null");
                    }
                    break;
                case BAIDU_AD:
                    baiDuAdHolder.init2(helper , mContext);
                    break;
            }
        }
    }

    /**
     *  这里是初始化上文下图的View;
     * @param helper
     */
    private void initUpText(BaseViewHolder helper) {

        TaNativeAds taNativeAds = TaNativeAds.newInstance();

        View leftTextAdView = taNativeAds.getUpText();

        ViewGroup itemView = (ViewGroup) helper.itemView;

        if (itemView.getChildCount()>0) {

            View view = itemView.getChildAt(0);
            //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
            if (view instanceof NativeExpressADView) {
                taNativeAds.releaseUpText(((MyTMNaTmView) view));
            }
            itemView.removeAllViews();
        }

        itemView.addView(leftTextAdView);
    }


    private void initTaLeftTa(BaseViewHolder helper) {

        TaNativeAds taNativeAds = TaNativeAds.newInstance();

        View leftTextAdView = taNativeAds.getLeftText();


        ViewGroup itemView = (ViewGroup) helper.itemView;

        if (itemView.getChildCount()>0) {

            View view = itemView.getChildAt(0);
            //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
            if (view instanceof LinearLayout) {
                taNativeAds.releaseLeftText(view);
            }

            itemView.removeAllViews();
        }

        itemView.addView(leftTextAdView);
    }


    /*
    这里要做的一个操作就是添加广点通的广告；
    */
    private void doAddGDTView(BaseViewHolder helper) {

        GdtNativeAds gdtNativeAds = GdtNativeAds.newInstance();

        NativeExpressADView gdtadView = gdtNativeAds.getAdView();

        if (gdtadView != null) {

            ViewGroup itemView = (ViewGroup) helper.itemView;

            if (itemView.getChildCount()>0) {

                View view = itemView.getChildAt(0);
                //这样写还是有点问题的，比如activity结束后没有办法释放View给Gdt的View的；
                if (view instanceof NativeExpressADView) {
                    gdtNativeAds.removeADView(((NativeExpressADView) view));
                }

                itemView.removeAllViews();
            }
            if (gdtadView != null) {
                itemView.addView(gdtadView);
                gdtadView.render();
            }
        }
    }
}
