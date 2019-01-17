package com.huanxi.renrentoutiao.presenter.ads.ta;

import android.util.Log;
import android.view.View;

import com.db.ta.sdk.TMBrTmView;
import com.db.ta.sdk.TmListener;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;

/**
 * Created by Dinosa on 2018/2/22.
 */

public class TaBannerPresenter {

    private TMBrTmView mTMBrAdView;


    public  final static int RETRY_COUNT=3;   //获取广告失败后重新尝试的次数；
    public   int startRetry=0;   //获取广告失败后重新尝试的次数；

    public void init(View view){


        mTMBrAdView = (TMBrTmView) view.findViewById(R.id.TMBrView);
        mTMBrAdView.setAdListener(new TmListener() {
            @Override
            public void onReceiveAd() {
                Log.d("========", "onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd() {
                Log.d("========", "onFailedToReceiveAd");
                //获取失败的话,重新加载;

                if(startRetry < RETRY_COUNT){
                    mTMBrAdView.loadAd(ConstantAd.TuiAAD.BANNER);
                    startRetry++;
                }
            }

            @Override
            public void onLoadFailed() {
                //获取失败重新加载;
                Log.d("========", "onLoadFailed");

                if(startRetry < RETRY_COUNT){
                    mTMBrAdView.loadAd(ConstantAd.TuiAAD.BANNER);
                    startRetry++;
                }

            }

            @Override
            public void onCloseClick() {
                Log.d("========", "onCloseClick");
            }

            @Override
            public void onAdClick() {
                Log.d("========", "onClick");
            }

            @Override
            public void onAdExposure() {
                Log.d("========", "onAdExposure");
            }
        });
//        mTMBrAdView.loadAd(72);//加载对应广告id
         mTMBrAdView.loadAd(ConstantAd.TuiAAD.BANNER);//加载对应广告id
//        mTMBrAdView.loadAd(344);

    }

}
