package com.huanxi.renrentoutiao.ui.view.refresh;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.huanxi.renrentoutiao.utils.frameAnimationUtils.AnimationsContainer;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class MyClassicsHeader extends ClassicsHeader{

    private ImageView mIconView;
    private AnimationsContainer.FramesSequenceAnimation mProgressDialogAnim;

    public MyClassicsHeader(Context context) {
        this(context,null);
    }

    public MyClassicsHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //这里要做的一个操作就是清除；

        if (mArrowView.getParent() != null) {
            ((ViewGroup) mArrowView.getParent()).removeView(mArrowView);
        }

        if(mProgressView.getParent()!=null){
            ((ViewGroup) mProgressView.getParent()).removeView(mProgressView);
        }

        if (mLastUpdateText.getParent()!=null) {
            ((ViewGroup) mLastUpdateText.getParent()).removeView(mLastUpdateText);
        }

        //这里还要添加一个ImageView;
        mIconView = new ImageView(context);
        mIconView.setImageResource(R.drawable.refresh_00024);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        ViewGroup parent = (ViewGroup) mTitleText.getParent();
        params.bottomMargin=9;
        mIconView.setLayoutParams(params);
        parent.addView(mIconView,0);

        mTitleText.setTextSize(UIUtils.dip2px(parent.getContext(),4));

        mProgressDialogAnim = AnimationsContainer.getInstance(R.array.refresh_anim,25).createProgressDialogAnim(mIconView);

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {
        super.onPulling(percent, offset, height, extendHeight);
        //这里是下拉的逻辑

        //执行动画；
/*        if(percent<=1){
            Float evaluate = evaluate(percent, 0, 180);
            mIconView.setRotation(evaluate);
        }
        System.out.println("MyClassHeader:onPulling:percent: "+percent+" offset: "+offset+" height:"+height+" extendHeight: "+extendHeight);
  */  }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {
        super.onReleasing(percent, offset, height, extendHeight);
        //执行动画；
/*        float myPercent=0;
        Float evaluate=0f;
        if(percent<=1){
            myPercent=1-percent;
            evaluate = evaluate(myPercent, 180,360);
            mIconView.setRotation(evaluate);
        }
        System.out.println("MyClassHeader:onReleasing:percent: "+percent+" offset: "+offset+" height:"+height+" extendHeight: "+extendHeight+" myPercent:"+myPercent+" evaluate"+evaluate);
   */ }


    /**
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }


    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        super.onStateChanged(refreshLayout,oldState,newState);
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mIconView.setImageResource(R.drawable.refresh_00024);
                System.out.println("PullDownToRefresh");
                break;
            case Refreshing:
                //这里播放帧动画；
                System.out.println("Refreshing");
                break;
            case RefreshReleased:

                mProgressDialogAnim.start();
                System.out.println("refresgRelease");
                break;
            case ReleaseToRefresh:

                System.out.println("ReleaseToRefresh");
                break;
            case ReleaseToTwoLevel:
                System.out.println("ReleaseToTwoLevel");
                break;
            case Loading:

                break;
        }
    }


}
