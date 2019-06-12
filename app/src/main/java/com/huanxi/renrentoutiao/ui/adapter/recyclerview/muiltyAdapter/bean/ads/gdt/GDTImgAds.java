package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.gdt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.net.api.ads.ApiEndReadAd;
import com.huanxi.renrentoutiao.net.api.ads.ApiStartReadAd;
import com.huanxi.renrentoutiao.net.api.news.ApiEndReadIssure;
import com.huanxi.renrentoutiao.net.api.news.ApiStartReadIssure;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResReadAwarad;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.utils.Utils;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Dinosa on 2018/3/20.
 * 广点通的贴片广告的一个广告类；
 */

public class GDTImgAds implements NativeExpressAD.NativeExpressADListener{

    public static final String TAG = GDTImgAds.class.getSimpleName();


    int AD_COUNT = 10;
    private NativeExpressAD mImgAdManager;

    public List<NativeExpressADView> mImgAds;
    private NativeExpressAD.NativeExpressADListener mListener;
    private BaseActivity mActivity;

    public GDTImgAds(OnAdReceived onAdReceived , BaseActivity activity) {
        mImgAds=new ArrayList<>();
        this.mOnAdReceived = onAdReceived;
        this.mActivity = activity;
        //这里去请求网络；
        initImageAds();
//        initLeftImageAds();
//        initRightImageAds();
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
    public GDTImgAds(OnAdReceived onAdReceived, int adCount, BaseActivity activity , NativeExpressAD.NativeExpressADListener listener) {
        mImgAds=new ArrayList<>();
        this.mOnAdReceived=onAdReceived;
        this.AD_COUNT=adCount;
        this.mActivity = activity;
        mListener = listener;
        //这里去请求网络；
        initImageAds();
    }

    /**
     * 这里是纯图的广告；
     */
    private void initImageAds() {
        Random random = new Random();
        int position = random.nextInt(2);
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.FULL_WIDTH); // 消息流中用AUTO_HEIGHT
        mImgAdManager = new NativeExpressAD(MyApplication.getConstantContext(), adSize,
                ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.AD_CODE_3[position], this);
        mImgAdManager.loadAD(AD_COUNT);
    }

    /**
     * 这里是左图右文的广告；
     */
    private void initLeftImageAds() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.FULL_WIDTH); // 消息流中用AUTO_HEIGHT
        mImgAdManager = new NativeExpressAD(MyApplication.getConstantContext(), adSize,
                ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.IMG_LEFT_TEXT_RIGHT, this);
        mImgAdManager.loadAD(AD_COUNT);
    }

    /**
     * 这里是左文右图的广告；
     */
    private void initRightImageAds() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.FULL_WIDTH); // 消息流中用AUTO_HEIGHT
        mImgAdManager = new NativeExpressAD(MyApplication.getConstantContext(), adSize,
                ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.TEXT_LEFT_IMG_RIGHT, this);
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
                int id = new Random().nextInt(9999) + 10000;
                nativeExpressADView.setId(id);
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

        if(mActivity != null) {
            Log.i("infos" , "adViewId=============="+adView.getId());
//            getBeganStart(adView.getId()+"");
            Utils.getBeganStart(mActivity , adView.getId()+"");
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

    /**
     * 这里开始计时操作?
     */
    public  void getBeganStart(String adId){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiStartReadAd.FROM_UID, mActivity.getUserBean().getUserid());
        paramsMap.put(ApiStartReadAd.ADID , adId);
        paramsMap.put(ApiStartReadAd.TYPE , "90");
        ApiStartReadAd apiStartReadAd = new ApiStartReadAd(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {
//                mActivity.requestStartCountDown();
                getReadCoin(adId);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (e instanceof HttpTimeException) {
                    HttpTimeException exception = (HttpTimeException) e;
                    //这里表示已经领取过该任务了
                    Toast toast = Toast.makeText(mActivity , exception.getMessage() , Toast.LENGTH_SHORT);
                    TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
                    v.setTextSize(20);
                    toast.show();
//                   mActivity.hideReadProgress();
                }
            }
        }, mActivity ,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiStartReadAd);
    }

    /**
     * 结束计时操作
     */
    public void getReadCoin(String adId) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiEndReadAd.FROM_UID,mActivity.getUserBean().getUserid());
        paramsMap.put(ApiEndReadAd.ADID , adId);
        paramsMap.put(ApiStartReadAd.TYPE , "90");
        ApiEndReadAd apiEndReadAd = new ApiEndReadAd(new HttpOnNextListener<ResReadAwarad>() {
            @Override
            public void onNext(ResReadAwarad resReadNewsAward) {
                Log.i("info" , "ApiEndReadAd="+resReadNewsAward.toString());
                // 显示获取金币

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },mActivity,paramsMap);
        HttpManager.getInstance().doHttpDeal(apiEndReadAd);
    }

}
