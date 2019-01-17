package com.huanxi.renrentoutiao.presenter.ads.ta;

import android.util.Log;
import android.view.View;

import com.db.ta.sdk.TMNaTmView;
import com.db.ta.sdk.TmListener;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;

/**
 * Created by Dinosa on 2018/2/22.
 *
 * 推啊的左文右图的业务逻辑类；
 */

public class TaLeftTextRightImgPresenter {

    private TMNaTmView mTMNaAdView;

    public void init(View view){

        mTMNaAdView = (TMNaTmView) view.findViewById(R.id.TMNaView);
        mTMNaAdView.setAdListener(new TmListener() {
            @Override
            public void onReceiveAd() {
                Log.d("========", "onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd() {
                Log.d("========", "onFailedToReceiveAd");
            }

            @Override
            public void onLoadFailed() {
                Log.d("========", "onLoadFailed");
            }

            @Override
            public void onCloseClick() {
                Log.d("========", "onCloseClick");
            }

            @Override
            public void onAdClick() {
                Log.d("========", "onAdClick");
            }

            @Override
            public void onAdExposure() {
                Log.d("========", "onAdExposure");
            }
        });
        //这里是左文右图；
        mTMNaAdView.loadAd(ConstantAd.TuiAAD.INFO_FLOW_LEFT_TEXT_RIGHT_IMG);
    }

}
