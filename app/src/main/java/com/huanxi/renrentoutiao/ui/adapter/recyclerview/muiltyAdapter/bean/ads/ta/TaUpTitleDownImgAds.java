package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.ta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.db.ta.sdk.TMNaTmView;
import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.presenter.ads.ta.MyTMNaTmView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dinosa on 2018/4/11.
 * 这个是推啊的左文有图；
 */

public class TaUpTitleDownImgAds {

    Context mContext;

    LinkedList<View> mFlowInfoUpTextAds=new LinkedList<>();

    List<View> mAllAds=new ArrayList<>();


    public TaUpTitleDownImgAds() {
        mContext = MyApplication.getConstantContext();
        initTaAds();
    }

    /**
     * 这里是初始化推啊的广告；
     */
    private void initTaAds() {
        initFiveUpTextAds();
    }


    public void initFiveUpTextAds(){
        for(int i=0;i<5;i++){
            //这里是上文下图的广告；
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_ta_up_text_down_img, null);

            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);

            mFlowInfoUpTextAds.add(view);
            mAllAds.add(view);
        }
    }

    /**
     * 这里是需要销毁所有的试图；
     */
    public void destory(){

        if (mAllAds != null && mAllAds.size()>0) {
            for (View allAd : mAllAds) {
                TMNaTmView taView = (TMNaTmView) allAd.findViewById(R.id.TMNaView);
                taView.destroy();
            }
        }
    }

    /**
     * 获取一个tui的广告；
     * @return
     */
    public  View getView(){

        if (mFlowInfoUpTextAds != null && mFlowInfoUpTextAds.size()>0) {
            View view = mFlowInfoUpTextAds.removeFirst();

            if(mFlowInfoUpTextAds.size()<2){
                initFiveUpTextAds();
            }

            MyTMNaTmView myTMNaTmView =(MyTMNaTmView) view.findViewById(R.id.TMNaView);
            myTMNaTmView.loadAd(ConstantAd.TuiAAD.INFO_FLOW_UP_TEXT_DOWN_IMG);

            return view;
        }else{
            //请求请求数据；
            initFiveUpTextAds();
        }
        return  null;
    }

}
