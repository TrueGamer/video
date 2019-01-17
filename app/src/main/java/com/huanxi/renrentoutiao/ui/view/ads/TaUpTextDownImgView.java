package com.huanxi.renrentoutiao.ui.view.ads;

import android.content.Context;
import android.util.AttributeSet;

import com.db.ta.sdk.TMNaTmView;
import com.db.ta.sdk.TmListener;

/**
 * Created by Dinosa on 2018/2/24.
 */

public class TaUpTextDownImgView extends TMNaTmView implements TmListener {
    public TaUpTextDownImgView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }

    public TaUpTextDownImgView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setAdListener(this);
    }

    @Override
    public void onReceiveAd() {
        isloaded=true;
    }

    @Override
    public void onFailedToReceiveAd() {

    }

    @Override
    public void onLoadFailed() {
        isloaded=false;
    }

    @Override
    public void onCloseClick() {

    }

    @Override
    public void onAdClick() {

    }

    @Override
    public void onAdExposure() {

    }

    private boolean isloaded=false;

    public boolean isLoaded(){
        return isloaded;
    }

    @Override
    public void loadAd(int i) {
        if(!isloaded){
            super.loadAd(i);
        }
    }
}
