package com.huanxi.renrentoutiao.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/4/3.
 * 新闻的刷新banner;
 */

public class NewsRefreshBanner extends FrameLayout {

    private TextView mTvTitle;

    public NewsRefreshBanner(@NonNull Context context) {
        this(context,null);
    }

    public NewsRefreshBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NewsRefreshBanner(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //这里我们要加载布局；

        View.inflate(getContext(), R.layout.view_news_refresh_banner_layout,this);

        mTvTitle = ((TextView) findViewById(R.id.tv_text));
        setAlpha(0);
    }

    public void show(int contentNumber){
        //这里我们做一个动画：
        mTvTitle.setText("「云赚视频」  已为你刷新"+contentNumber+"条");
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(this,"Alpha",0,1);
        objectAnimator.setDuration(1000);

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //这里延时在做一个动画消失

                postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(NewsRefreshBanner.this,"Alpha",1,0);
                        objectAnimator.setDuration(1000);
                        objectAnimator.start();

                    }
                },500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();
    }
}
