package com.huanxi.renrentoutiao.ui.view.ads;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.db.ta.sdk.TMAwView;
import com.db.ta.sdk.TmListener;
import com.huanxi.renrentoutiao.globle.ConstantAd;

/**
 * Created by Dinosa on 2018/3/14.
 */

public class TAFloatView extends TMAwView {


    public TAFloatView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }

    public TAFloatView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);

        setAdListener(new TmListener() {
            @Override
            public void onReceiveAd() {
                Log.d("========", "onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd() {
                Log.d("========", "onFailedToReceiveAd");
                load();
            }

            @Override
            public void onLoadFailed() {
                Log.d("========", "onLoadFailed");
               load();
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

    }

    public void load(){
        loadAd(ConstantAd.TuiAAD.ICON_FLOAT);
    }
}
