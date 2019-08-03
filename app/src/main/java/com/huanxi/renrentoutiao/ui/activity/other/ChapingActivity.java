package com.huanxi.renrentoutiao.ui.activity.other;


import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import com.huanxi.renrentoutiao.R;

import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.comm.util.AdError;


/**
 * Created by Dinosa on 2018/1/26.
 */

public class ChapingActivity extends BaseActivity {
    private UnifiedInterstitialAD iad;
    @Override
    public int getContentLayout() {
        return R.layout.chaping;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        setStatusBarImmersive(null);
        iad = new UnifiedInterstitialAD(this, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.POPUPWINDOW_AD, new UnifiedInterstitialADListener(){
            private final String TAG = "ZT";
            @Override
            public void onADReceive() {
                iad.showAsPopupWindow();
            }

            @Override
            public void onNoAD(AdError error) {
                Log.i(TAG, "onNoAD");
                finish();
            }

            @Override
            public void onADOpened() {
                Log.i(TAG, "onADOpened");
            }

            @Override
            public void onADExposure() {
                Log.i(TAG, "onADExposure");
            }

            @Override
            public void onADClicked() {
                Log.i(TAG, "onADClicked : " + (iad.getExt() != null? iad.getExt().get("clickUrl") : ""));
            }

            @Override
            public void onADLeftApplication() {
                Log.i(TAG, "onADLeftApplication");
            }

            @Override
            public void onADClosed() {
                Log.i(TAG, "onADClosed");
                iad.destroy();
                finish();
            }
        });
        iad.loadAD();
    }

}
