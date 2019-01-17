package com.huanxi.renrentoutiao.presenter.ads.gdt;

import android.util.Log;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/3/20.
 * 广点通的贴片广告的一个广告类；
 */

public class GDTImgAds implements NativeExpressAD.NativeExpressADListener{

    public static final String TAG = GDTImgAds.class.getSimpleName();


    int AD_COUNT=10;
    private NativeExpressAD mImgAdManager;

    public List<NativeExpressADView> mImgAds;
    private NativeExpressAD.NativeExpressADListener mListener;
    private BaseActivity mActivity;

    public GDTImgAds(OnAdReceived onAdReceived) {
        mImgAds=new ArrayList<>();
        this.mOnAdReceived=onAdReceived;
        //这里去请求网络；
        initImageAds();
    }

    /**
     * 这里是获取指定数量的广告的操作；
     * @param onAdReceived
     * @param adCount
     */
    public GDTImgAds(OnAdReceived onAdReceived, int adCount) {
        mImgAds=new ArrayList<>();
        this.mOnAdReceived=onAdReceived;
        this.AD_COUNT=adCount;
        //这里去请求网络；
        initImageAds();
    }

    /**
     * 这里是获取指定数量的广告的操作；
     * @param onAdReceived
     * @param adCount
     */
    public GDTImgAds(OnAdReceived onAdReceived, int adCount, NativeExpressAD.NativeExpressADListener listener) {
        mImgAds=new ArrayList<>();
        this.mOnAdReceived=onAdReceived;
        this.AD_COUNT=adCount;
        mListener = listener;
        //这里去请求网络；
        initImageAds();
    }

    /**
     * 这里是纯图的广告；
     */
    private void initImageAds() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.FULL_WIDTH); // 消息流中用AUTO_HEIGHT
        mImgAdManager = new NativeExpressAD(MyApplication.getConstantContext(), adSize, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.NATIVE_VIDEO_IMG, this);
        mImgAdManager.loadAD(AD_COUNT);
    }


    @Override
    public void onNoAD(AdError adError) {
        Log.i(
                TAG,
                String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                        adError.getErrorMsg()));

        if (mListener != null) {
            mListener.onNoAD(adError);
        }
    }

    @Override
    public void onADLoaded(List<NativeExpressADView> list) {
        //这里我们采集得到广点通的广告；
        //这里做一个线程池操作；
        if (list != null) {
            for (NativeExpressADView nativeExpressADView : list) {
                mImgAds.add(nativeExpressADView);
            }

            if (mOnAdReceived != null) {
                mOnAdReceived.onGdtImgAdReceived(list);
            }
            if (mListener != null) {
                mListener.onADLoaded(list);
            }
        }
    }

    @Override
    public void onRenderFail(NativeExpressADView adView) {
        Log.i(TAG, "onRenderFail: " + adView.toString());


        if (mListener != null) {
            mListener.onRenderFail(adView);
        }
    }

    @Override
    public void onRenderSuccess(NativeExpressADView adView) {
        Log.i(TAG, "onRenderSuccess: " + adView.toString());


        if (mListener != null) {
            mListener.onRenderSuccess(adView);
        }
    }

    @Override
    public void onADExposure(NativeExpressADView adView) {
        Log.i(TAG, "onADExposure: " + adView.toString());

        if (mListener != null) {
            mListener.onADExposure(adView);
        }
    }

    @Override
    public void onADClicked(NativeExpressADView adView) {
        Log.i(TAG, "onADClicked: " + adView.toString());
        if (mListener != null) {
            mListener.onADClicked(adView);
        }
    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
        //这里是广告点击关闭的的一个操作；
        if (mListener != null) {
            mListener.onADClosed(nativeExpressADView);
        }
    }
    @Override
    public void onADLeftApplication(NativeExpressADView adView) {
        Log.i(TAG, "onADLeftApplication: " + adView.toString());

        if (mListener != null) {
            mListener.onADLeftApplication(adView);
        }
    }

    @Override
    public void onADOpenOverlay(NativeExpressADView adView) {
        Log.i(TAG, "onADOpenOverlay: " + adView.toString());

        if (mListener != null) {
            mListener.onADOpenOverlay(adView);
        }
    }

    @Override
    public void onADCloseOverlay(NativeExpressADView adView) {
        Log.i(TAG, "onADCloseOverlay");

        if (mListener != null) {
            mListener.onADCloseOverlay(adView);
        }
    }


    public void load(){
        //这里我们每次要加载广告的操作；
        mImgAdManager.loadAD(AD_COUNT);
    }

    /**
     * 所有的广告；
     */
    public void destory(){
        if (mImgAds != null) {
            for (NativeExpressADView imgAd : mImgAds) {
                imgAd.destroy();
            }
        }
    }

    OnAdReceived mOnAdReceived;


    public interface OnAdReceived{
        public void onGdtImgAdReceived(List<NativeExpressADView> mImgAds);
    }
}
