package com.huanxi.renrentoutiao.presenter.ads.ta;

import android.content.Context;

import com.db.ta.sdk.NonStandardTm;
import com.db.ta.sdk.NsTmListener;
import com.google.gson.Gson;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.net.bean.ResTaCustomAdBean;

/**
 * Created by Dinosa on 2018/3/17.
 *
 */

public class MyTACustomBanner extends NonStandardTm {

    private final TuiYaCustomerBanner mBanner;
    private ResTaCustomAdBean mResTaCustomAdBean;

    public  final static int RETRY_COUNT=3;   //获取广告失败后重新尝试的次数；
    public   int startRetry=0;   //获取广告失败后重新尝试的次数；


    NsTmListener mNsTmListener = new NsTmListener() {
        @Override
        public void onReceiveAd(String s) {

            Gson gson = new Gson();
            mResTaCustomAdBean = gson.fromJson(s, ResTaCustomAdBean.class);
            if (mOnReceiveAdListener != null) {
                mOnReceiveAdListener.onReceiveAd(mResTaCustomAdBean);
            }
        }

        @Override
        public void onFailedToReceiveAd() {
            System.out.println(MyTACustomBanner.class.getSimpleName()+" :onFailedToReceiveAd");
            if(startRetry < RETRY_COUNT){
                loadAd(ConstantAd.TuiAAD.CUSTOM_AD);
                startRetry++;
            }
        }
    };

    public MyTACustomBanner(TuiYaCustomerBanner banner, Context context,OnReceiveAdListener onReceiveAdListener) {
        super(context);
        mBanner = banner;
        this.mOnReceiveAdListener=onReceiveAdListener;

        loadAd(ConstantAd.TuiAAD.CUSTOM_AD);

        setAdListener(mNsTmListener);
    }

    @Override
    public void destroy() {
        //super.destroy();
    }

    /**
     * 广告来的时候的回掉操作；
     */
    public interface OnReceiveAdListener{
        public void onReceiveAd(ResTaCustomAdBean bean);
    }

    public OnReceiveAdListener mOnReceiveAdListener;


    public OnReceiveAdListener getOnReceiveAdListener() {
        return mOnReceiveAdListener;
    }

    public void setOnReceiveAdListener(OnReceiveAdListener onReceiveAdListener) {
        mOnReceiveAdListener = onReceiveAdListener;
    }
}
