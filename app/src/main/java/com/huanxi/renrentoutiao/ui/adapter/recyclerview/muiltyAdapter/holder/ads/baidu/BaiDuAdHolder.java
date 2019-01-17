package com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.holder.ads.baidu;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.base.BaseMuiltyViewHolder;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.muiltyAdapter.bean.ads.baidu.BaiDuBannerBean;
import com.huanxi.renrentoutiao.utils.Utils;

import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Dinosa on 2018/4/10.
 */

public class BaiDuAdHolder extends BaseMuiltyViewHolder<BaiDuBannerBean> {

    @Override
    public void init(final BaiDuBannerBean baiDuBannerBean, BaseViewHolder holder, final Context context) {

        ViewGroup adContainer = (ViewGroup) holder.itemView;

        Random random = new Random();
        int position = random.nextInt(3);

        String adPlaceId = ConstantAd.BAIDUAD.BANNER[position]; //  重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        AdView adView = new AdView(context , adPlaceId);
        // 设置监听器
        adView.setClickable(false);
        adView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        adView.setListener(new AdViewListener() {
            public void onAdSwitch() {
                Log.w("", "onAdSwitch");
            }

            public void onAdShow(JSONObject info) {
                // 广告已经渲染出来
                Log.w("", "onAdShow " + info.toString());
            }

            public void onAdReady(AdView adView) {
                // 资源已经缓存完毕，还没有渲染出来
                Log.w("", "onAdReady " + adView);
            }

            public void onAdFailed(String reason) {
                Log.w("", "onAdFailed " + reason);
            }

            public void onAdClick(JSONObject info) {
                Log.w("", "onAdClick " + info.toString());
//                Utils.getBeganStart((BaseActivity) context , baiDuBannerBean.getAdid()+"");
            }

            @Override
            public void onAdClose(JSONObject arg0) {
                Log.w("", "onAdClose");
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width * 3 / 7;
        Log.i("info" , "winW="+winW+",winH="+winH+",height="+height+",width="+width);
        // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
        rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
        adContainer.addView(adView, rllp);

    }

    public void init2(BaseViewHolder holder, final Context context) {

        ViewGroup adContainer = (ViewGroup) holder.itemView;

        Random random = new Random();
        int position = random.nextInt(3);

        String adPlaceId = ConstantAd.BAIDUAD.BANNER[position]; //  重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        AdView adView = new AdView(context , adPlaceId);
        // 设置监听器
        adView.setClickable(false);
        adView.setListener(new AdViewListener() {
            public void onAdSwitch() {
                Log.w("", "onAdSwitch");
            }

            public void onAdShow(JSONObject info) {
                // 广告已经渲染出来
                Log.w("", "onAdShow " + info.toString());
            }

            public void onAdReady(AdView adView) {
                // 资源已经缓存完毕，还没有渲染出来
                Log.w("", "onAdReady " + adView);
            }

            public void onAdFailed(String reason) {
                Log.w("", "onAdFailed " + reason);
            }

            public void onAdClick(JSONObject info) {
                 Log.w("", "onAdClick2 " + info.toString());
            }

            @Override
            public void onAdClose(JSONObject arg0) {
                Log.w("", "onAdClose");
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width * 3 / 7;
        Log.i("info" , "winW="+winW+",winH="+winH+",height="+height+",width="+width);
        // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
        rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
        adContainer.addView(adView, rllp);
        adContainer.setClickable(false);

    }

}
