package com.huanxi.renrentoutiao.ui.fragment.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.db.ta.sdk.TmActivity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.other.SplashActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/4/17.
 * 这里是h5的开屏广告；
 */

public class SplashH5AdFragment extends BaseFragment {

    @BindView(R.id.iv_ad_bg)
    ImageView mIvAdBg;

    @BindView(R.id.ll_ads)
    View mLlAds;

    @BindView(R.id.skip_view)
    TextView mTvSkipView;


    @Override
    protected View getContentView() {
        return inflatLayout(R.layout.fragment_h5_splash);
    }

    @Override
    protected void initData() {
        super.initData();



        mImgUrl = getArguments().getString(IMG_URL);
        mClickUrl=getArguments().getString(CLICK_URL);

        Glide.with(getBaseActivity())
                .load(mImgUrl)
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                       mIvAdBg.setImageDrawable(resource);
                        //这里我们要开始计时的操作；
                        startCountDown(6);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        //这里我们要做的一个操作就是显示直接跳转；到目标的Activity;
                    }
                });

    }
    CountDownTimer mCountDownTimer=null;

    /**
     * 开始一个广告的倒计时的操作
     */
    private void startCountDown(int totalTime) {
        int timeUnit=1000;
        totalTime=totalTime*1000;
        //去做一个更新的操作；
        mCountDownTimer = new CountDownTimer(totalTime,timeUnit) {
            @Override
            public void onTick(long millisUntilFinished) {
                //去做一个更新的操作；
                mTvSkipView.setText("跳过 "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                //这里我们将修改状态；变成可领取的状态；
                //startPlay();
                ((SplashActivity) getBaseActivity()).startActivity();
                getBaseActivity().finish();
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @OnClick(R.id.ll_ads)
    public void onClickAds(){
        //这里要做的一个操作就是跳转的操作；
       /* Intent intent = WebHelperActivity.getIntent(getBaseActivity(), mClickUrl, "", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

        ((SplashActivity) getBaseActivity()).startActivity();
        TmActivity.a(getBaseActivity(),mClickUrl);
        getBaseActivity().finish();
    }


    @OnClick(R.id.skip_view)
    public void onClickSkip(){
        //跳过的逻辑；
        ((SplashActivity) getBaseActivity()).startActivity();
        getBaseActivity().finish();
    }

    public String mImgUrl;
    public String mClickUrl;


    public static final String IMG_URL="imgUrl";
    public static final String CLICK_URL="clickUrl";

    /**
     * 这里要做的一个
     * @param context
     * @param imgUrl
     * @param clickUrl
     * @return
     */
    public static BaseFragment getSplashH5Fragment(Context context,String imgUrl,String clickUrl){

        Bundle bundle = new Bundle();
        bundle.putString(IMG_URL,imgUrl);
        bundle.putString(CLICK_URL,clickUrl);
        SplashH5AdFragment splashH5AdFragment = new SplashH5AdFragment();
        splashH5AdFragment.setArguments(bundle);
        return splashH5AdFragment;
    }

}
