package com.huanxi.renrentoutiao.ui.view.ads;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;

/**
 * Created by Dinosa on 2018/2/10.
 *  这里我们来实现相应的操作；
 */

public class GDTBannerView extends FrameLayout {

    private final Activity mActivity;

    BannerView bv;
    ViewGroup bannerContainer;

    public GDTBannerView(@NonNull Context context) {
        this(context,null);
    }

    public GDTBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GDTBannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mActivity = (Activity) context;
        LayoutInflater.from(mActivity).inflate(R.layout.item_gdt_banner_ad,this,true);
        init(this);
    }


    public void init(View viewGroup){
        bannerContainer=(ViewGroup) viewGroup.findViewById(R.id.bannerContainer);
        initBanner(mActivity);

    }

    protected void initBanner(Activity context) {
        this.bv = new BannerView(context, ADSize.BANNER, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.BANNER_AD);
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i(
                        "AD_DEMO",
                        String.format("Banner onNoAD，eCode = %d, eMsg = %s", error.getErrorCode(),
                                error.getErrorMsg()));
                bv.loadAD();
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        bannerContainer.addView(bv);
    }

    public void init(){
        bv.loadAD();
    }

    //销毁；
    public void desory(){
        if (bv != null) {
            bv.destroy();
        }
    }
}
