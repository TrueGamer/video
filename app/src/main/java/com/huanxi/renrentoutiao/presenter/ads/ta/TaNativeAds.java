package com.huanxi.renrentoutiao.presenter.ads.ta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.ta.sdk.TmListener;
import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.presenter.ads.gdt.GdtNativeAds;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.LinkedList;

/**
 * Created by Dinosa on 2018/3/1.
 *
 * 这里是推啊的一个原生广告；
 */

public class TaNativeAds implements TmListener{
    private final Context mContext;

    //这几将ads进行一个封装；

    public LinkedList<NativeExpressADView> mAds=new LinkedList<>();

    public static TaNativeAds mNativeAds;

    private TaNativeAds() {
        mContext = MyApplication.getConstantContext();
        initTaAds();
    }


    public static TaNativeAds newInstance(){

        if (mNativeAds == null) {
            synchronized (GdtNativeAds.class){
                mNativeAds=new TaNativeAds();
            }
        }
        return mNativeAds;
    }
    //这里是信息流中的广告；
    LinkedList<View> mFlowInfoUpTextAds =new LinkedList<>();

    LinkedList<View> mFlowInfoLeftTextAds =new LinkedList<>();

    /**
     * 这里初始化推啊的广告；
     * 这里要做的一个操作就是处理化View的操作；
     */
    private void initTaAds() {

        initFiveLeftTextAds();
        initFiveUpTextAds();
    }

    public void initFiveUpTextAds(){
        for(int i=0;i<5;i++){
            //这里是上文下图的广告；
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_ta_up_text_down_img, null);

           // MyTMNaTmView tmView = (MyTMNaTmView) view.findViewById(R.id.TMNaView);
            //tmView.loadAd(ConstantAd.TuiAAD.INFO_FLOW_UP_TEXT_DOWN_IMG);
            mFlowInfoUpTextAds.add(view);
        }
    }

    public void initFiveLeftTextAds(){

        for(int i=0;i<5;i++){
            //这里是上文下图的广告；
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_ta_left_text_right_img, null);

            //MyTMNaTmView tmView = (MyTMNaTmView) view.findViewById(R.id.TMNaView);
            mFlowInfoLeftTextAds.add(view);
        }
    }

    public View getUpText(){

        if (mFlowInfoUpTextAds.size()<1) {
            initFiveUpTextAds();
        }
        View view = mFlowInfoUpTextAds.removeFirst();
        MyTMNaTmView myTMNaTmView = (MyTMNaTmView)view.findViewById(R.id.TMNaView);
        myTMNaTmView.setAdListener(this);
        myTMNaTmView.loadAd(ConstantAd.TuiAAD.INFO_FLOW_UP_TEXT_DOWN_IMG);
        return view;
    }

    public void releaseUpText(View view){

        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        if(!mFlowInfoUpTextAds.contains(view)){
            mFlowInfoUpTextAds.add(view);
        }
    }

    public View getLeftText(){

        if (mFlowInfoLeftTextAds.size()<1) {
            //如果小于我们就手动初始化操作；
            initFiveLeftTextAds();
        }
        View view = mFlowInfoLeftTextAds.removeFirst();
        MyTMNaTmView myTMNaTmView =(MyTMNaTmView) view.findViewById(R.id.TMNaView);
        myTMNaTmView.loadAd(ConstantAd.TuiAAD.INFO_FLOW_LEFT_TEXT_RIGHT_IMG);
        return view;
    }

    public void releaseLeftText(View view){

        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        if(!mFlowInfoLeftTextAds.contains(view)){
            mFlowInfoLeftTextAds.add(view);
        }
    }

    //这里是一个回掉的操作；

    @Override
    public void onReceiveAd() {

    }

    @Override
    public void onFailedToReceiveAd() {

    }

    @Override
    public void onLoadFailed() {

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


}
