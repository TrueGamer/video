package com.huanxi.renrentoutiao.presenter.ads.ta;

import android.util.Log;
import android.view.View;

import com.db.ta.sdk.TMNaTmView;
import com.db.ta.sdk.TmListener;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;

/**
 * Created by Dinosa on 2018/2/24.
 * 推啊的上文下图的业务逻辑类；
 */

public class TaUpTextDownImgPresernter {

    TMNaTmView mTMNaAdView;
    public boolean isLoad=false;

    /**
     * 这里我们进一步进一个处理；
     */
    public void init(View view){

        mTMNaAdView = (TMNaTmView) view.findViewById(R.id.TMNaView);
        mTMNaAdView.setAdListener(new TmListener() {
            @Override
            public void onReceiveAd() {
                Log.d("========", "onReceiveAd");
                isLoad=true;
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
        //这里是上文下图；
        if (!isLoad) {
            mTMNaAdView.loadAd(ConstantAd.TuiAAD.INFO_FLOW_UP_TEXT_DOWN_IMG);
        }
    }
}
