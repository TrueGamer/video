package com.huanxi.renrentoutiao.ui.view.ads;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.List;

/**
 * Created by Dinosa on 2018/2/10.
 */

public class GDTUpTextDownImg extends FrameLayout  implements NativeExpressAD.NativeExpressADListener{

    private static final String TAG = GDTUpTextDownImg.class.getSimpleName();

    private NativeExpressAD nativeExpressAD;
    private NativeExpressADView nativeExpressADView;
    private ViewGroup container;

    public GDTUpTextDownImg(@NonNull Context context) {
        this(context,null);
    }

    public GDTUpTextDownImg(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GDTUpTextDownImg(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.nativelistitem,this);
        initView();
    }

    /*
     * 这里对其进行一个View进行一个初始化
     * */
    private void initView() {
        container= ((ViewGroup) findViewById(R.id.container));
        try {
            /**
             *  如果选择支持视频的模版样式，请使用{@link Constants#NativeExpressSupportVideoPosID}
             */
            nativeExpressAD = new NativeExpressAD(getContext(), getMyADSize(), ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.NEWS_UP_TEXT_DOWN_IMG_AD_2, this); // 这里的Context必须为Activity
            nativeExpressAD.setVideoOption(new VideoOption.Builder()
                    .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // 设置什么网络环境下可以自动播放视频
                    .setAutoPlayMuted(true) // 设置自动播放视频时，是否静音
                    .build()); // setVideoOption是可选的，开发者可根据需要选择是否配置
            nativeExpressAD.loadAD(1);
        } catch (NumberFormatException e) {
            Log.w(TAG, "ad size invalid.");
        }
    }

    @Override
    public void onNoAD(AdError adError) {
        System.out.println("新闻信息流： "+adError.getErrorMsg());
    }

    @Override
    public void onADLoaded(List<NativeExpressADView> list) {
        Log.i(TAG, "onADLoaded: " + list.size());
        // 释放前一个展示的NativeExpressADView的资源
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }

        if (container.getVisibility() != View.VISIBLE) {
            container.setVisibility(View.VISIBLE);
        }

        if (container.getChildCount() > 0) {
            container.removeAllViews();
        }

        nativeExpressADView = list.get(0);
        // 广告可见才会产生曝光，否则将无法产生收益。
        container.addView(nativeExpressADView);
        nativeExpressADView.render();
    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {

    }
    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
        Log.i(TAG, "onADClosed");
        // 当广告模板中的关闭按钮被点击时，广告将不再展示。NativeExpressADView也会被Destroy，释放资源，不可以再用来展示。
        if (container != null && container.getChildCount() > 0) {
            container.removeAllViews();
            container.setVisibility(View.GONE);
        }
    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

    }

    private ADSize getMyADSize() {
        int w =  ADSize.FULL_WIDTH ;
        int h =  ADSize.AUTO_HEIGHT ;
        return new ADSize(w, h);
    }

    /**
     * 加载图片；
     */
    public void init(){
        nativeExpressAD.loadAD(1);
    }
}
