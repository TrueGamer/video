package com.huanxi.renrentoutiao.ui.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.net.api.ads.ApiEndReadAd;
import com.huanxi.renrentoutiao.net.api.ads.ApiStartReadAd;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResReadAwarad;
import com.huanxi.renrentoutiao.ui.activity.other.SplashActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Dinosa on 2018/2/22.
 * 这里是splash的广点通的广告位；
 */

public class SplashGDTFragment extends BaseFragment implements SplashADListener{

    private SplashAD splashAD;



    @BindView(R.id.fl_splash_container)
    FrameLayout mFlSplashContainer;

    @BindView(R.id.skip_view)
    TextView mSkipView;


    //是否可以跳转；
    public boolean canJump = false;


    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_splash_gdt);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchSplashAD(getActivity(), mFlSplashContainer, mSkipView, ConstantAd.GdtAD.APPID, ConstantAd.GdtAD.SPLASH_AD, this, 0);
    }

    //=============下面是广点通的广告======================

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity        展示广告的activity
     * @param adContainer     展示广告的大容器
     * @param skipContainer   自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId           应用ID
     * @param posId           广告位ID
     * @param adListener      广告状态监听器
     * @param fetchDelay      拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

    @Override
    public void onADDismissed() {
        next();
        Log.e("splash","onADDismissed");
    }

    @Override
    public void onNoAD(AdError adError) {
        Log.e("splash",adError.getErrorMsg());
        /** 如果加载广告失败，则直接跳转 */
        if (getBaseActivity() != null) {

            ((SplashActivity) getBaseActivity()).startActivity();
            getBaseActivity().finish();
        }
    }

    @Override
    public void onADPresent() {
        Log.e("splash","onADPresent");
    }

    @Override
    public void onADClicked() {
        Log.e("splash","onADClicked");
    }

    @Override
    public void onADTick(long millisUntilFinished) {
        Log.e("splash","onADTick="+millisUntilFinished);
    }

    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
        if (canJump) {
            ((SplashActivity) getBaseActivity()).startActivity();
            getBaseActivity().finish();
        } else {
            canJump = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        canJump = false;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

}
