package com.huanxi.renrentoutiao.presenter.ads.gdt;

import android.util.Log;
import android.view.ViewGroup;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GDTImgAds;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/3/1.
 *  这里以后做一个广告池；
 */

public class GdtNativeAds  {

    //这几将ads进行一个封装；

    public LinkedList<NativeExpressADView> mUpTextDowmImgAds =new LinkedList<>();

    public LinkedList<NativeExpressADView> mImgAds =new LinkedList<>();

    public static GdtNativeAds mNativeAds;

    private GdtNativeAds() {
        initGDTAds();
    }

    public static GdtNativeAds newInstance(){

        if (mNativeAds == null) {
            synchronized (GdtNativeAds.class){
                mNativeAds=new GdtNativeAds();
            }
        }
        return mNativeAds;
    }


    int AD_COUNT=10;
    private NativeExpressAD mUpTextADManager;
    private NativeExpressAD mImgAdManager;

    /**
     * 这里我们要出初始化广点通的广告的一个操作；
     * 默认操作10条广告；
     */
    private void initGDTAds() {

        initUpTextDownImg();
        initImageAds();
    }

    /**
     * 这里是纯图的广告；
     */
    private void initImageAds() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.FULL_WIDTH); // 消息流中用AUTO_HEIGHT
        mImgAdManager = new NativeExpressAD(MyApplication.getConstantContext(), adSize, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.NATIVE_VIDEO_IMG, mImgAdListener);
        mImgAdManager.loadAD(AD_COUNT);
    }

    private void initUpTextDownImg() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.FULL_WIDTH); // 消息流中用AUTO_HEIGHT
        mUpTextADManager = new NativeExpressAD(MyApplication.getConstantContext(), adSize, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.NATIVE_VIDEO_IMG, mUpTextDownImgListener);
        mUpTextADManager.loadAD(AD_COUNT);
    }

    //==============这里要做是对广告的一个监听的一个操作；=======================

    public static final String TAG="HOME_FRAGMENT";



    NativeExpressAD.NativeExpressADListener mUpTextDownImgListener=new NativeExpressAD.NativeExpressADListener() {

        @Override
        public void onNoAD(AdError adError) {
            Log.i(
                    TAG,
                    String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                            adError.getErrorMsg()));
        }

        @Override
        public void onADLoaded(List<NativeExpressADView> list) {
            //这里我们采集得到广点通的广告；
            //这里做一个线程池操作；
            if (list != null) {
                for (NativeExpressADView nativeExpressADView : list) {
                    mUpTextDowmImgAds.add(nativeExpressADView);
                }
            }
        }

        @Override
        public void onRenderFail(NativeExpressADView adView) {
            Log.i(TAG, "onRenderFail: " + adView.toString());
        }

        @Override
        public void onRenderSuccess(NativeExpressADView adView) {
            Log.i(TAG, "onRenderSuccess: " + adView.toString());
        }

        @Override
        public void onADExposure(NativeExpressADView adView) {
            Log.i(TAG, "onADExposure: " + adView.toString());
        }

        @Override
        public void onADClicked(NativeExpressADView adView) {
            Log.i(TAG, "onADClicked: " + adView.toString());
        }

        @Override
        public void onADClosed(NativeExpressADView nativeExpressADView) {
            //这里是广告点击关闭的的一个操作；
        }
        @Override
        public void onADLeftApplication(NativeExpressADView adView) {
            Log.i(TAG, "onADLeftApplication: " + adView.toString());
        }

        @Override
        public void onADOpenOverlay(NativeExpressADView adView) {
            Log.i(TAG, "onADOpenOverlay: " + adView.toString());
        }

        @Override
        public void onADCloseOverlay(NativeExpressADView adView) {
            Log.i(TAG, "onADCloseOverlay");
        }

    } ;

    /**
     * 这里是回复广告
     * @param view
     */
    public void removeADView(NativeExpressADView view){

        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        if(!mUpTextDowmImgAds.contains(view)){
            mUpTextDowmImgAds.add(view);
        }
    }

    /**
     * 这里要做的一个操作就是获取一个AdView;
     * @return
     */
    public NativeExpressADView getAdView(){

        if (mUpTextDowmImgAds.size()>0) {

            NativeExpressADView nativeExpressADView = mUpTextDowmImgAds.removeFirst();

            if (nativeExpressADView.getParent() != null) {
                ((ViewGroup) nativeExpressADView.getParent()).removeView(nativeExpressADView);
            }

            if (mUpTextDowmImgAds.size()<AD_COUNT/2) {
                //这里要做的一个操作就是获取更多的View；
                mUpTextADManager.loadAD(AD_COUNT);
            }

            return nativeExpressADView;
        }
        // TODO: 2018/3/1  这里我们做对应的动态分配的；以后做；
        // 这里我们重新请求数据；
        new com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt.GDTImgAds(new GDTImgAds.OnAdReceived() {
            @Override
            public void onGdtImgAdReceived(List<NativeExpressADView> mImgAds) {
                if (mImgAds != null) {
                    mUpTextDowmImgAds.addAll(mImgAds);
                }
            }
        } , 10);

        if(mUpTextDowmImgAds.size()>0) {
            return mUpTextDowmImgAds.get(0);
        } else {
            return null;
        }
    }


    //下面要做的一个操作就是对纯图的广告的获取；
    NativeExpressAD.NativeExpressADListener mImgAdListener=new NativeExpressAD.NativeExpressADListener() {

        @Override
        public void onNoAD(AdError adError) {
            Log.i(
                    TAG,
                    String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                            adError.getErrorMsg()));
        }

        @Override
        public void onADLoaded(List<NativeExpressADView> list) {
            //这里我们采集得到广点通的广告；
            //这里做一个线程池操作；
            if (list != null) {
                for (NativeExpressADView nativeExpressADView : list) {
                    mImgAds.add(nativeExpressADView);
                }
            }
        }

        @Override
        public void onRenderFail(NativeExpressADView adView) {
            Log.i(TAG, "onRenderFail: " + adView.toString());
        }

        @Override
        public void onRenderSuccess(NativeExpressADView adView) {
            Log.i(TAG, "onRenderSuccess: " + adView.toString());
        }

        @Override
        public void onADExposure(NativeExpressADView adView) {
            Log.i(TAG, "onADExposure: " + adView.toString());
        }

        @Override
        public void onADClicked(NativeExpressADView adView) {
            Log.i(TAG, "onADClicked: " + adView.toString());
        }

        @Override
        public void onADClosed(NativeExpressADView nativeExpressADView) {
            //这里是广告点击关闭的的一个操作；
        }
        @Override
        public void onADLeftApplication(NativeExpressADView adView) {
            Log.i(TAG, "onADLeftApplication: " + adView.toString());
        }

        @Override
        public void onADOpenOverlay(NativeExpressADView adView) {
            Log.i(TAG, "onADOpenOverlay: " + adView.toString());
        }

        @Override
        public void onADCloseOverlay(NativeExpressADView adView) {
            Log.i(TAG, "onADCloseOverlay");
        }

    } ;


    /**
     * 这里是获取纯图的广告操作；
     * @return
     */
    public NativeExpressADView getImgAdView(){

        if (mImgAds.size()>0) {

            NativeExpressADView nativeExpressADView = mImgAds.removeFirst();

            if (nativeExpressADView.getParent() != null) {
                ((ViewGroup) nativeExpressADView.getParent()).removeView(nativeExpressADView);
            }

            if (mImgAds.size()<AD_COUNT/2) {
                //这里要做的一个操作就是获取更多的View；
                mImgAdManager.loadAD(AD_COUNT);
            }

            return nativeExpressADView;
        }else{
            if (mImgAds.size()<AD_COUNT/2) {
                //这里要做的一个操作就是获取更多的View；
                mImgAdManager.loadAD(AD_COUNT);
            }
        }
        //// TODO: 2018/3/1  这里我们做对应的动态分配的；以后做；
        //这里我们重新请求数据；
        return null;
    }


    public void releaseImgAdView(NativeExpressADView view){
        if(view !=null ){

            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }

            if(view!=null && !mImgAds.contains(view)){
                mImgAds.add(view);
            }

        }
    }

}
