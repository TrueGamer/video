package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * Created by Dinosa on 2018/2/6.
 * 这里我们需要做的一个操作就是监听进度
 */

public class MyDonutProgress extends DonutProgress {
    public MyDonutProgress(Context context) {
        super(context);
    }

    public MyDonutProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDonutProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这里我们要监听,加载的进度；
        if (mListener != null) {
            mListener.onProgress(getProgress());
        }
    }

    public  OnProgressListener mListener;

    public interface  OnProgressListener{

        public void onProgress(float progress);
    }

    public OnProgressListener getListener() {
        return mListener;
    }

    public void setOnProgressListener(OnProgressListener listener) {
        mListener = listener;
    }

    @Override
    public void setDonut_progress(String percent) {
        if(!TextUtils.isEmpty(percent)) {

            this.setProgress((float)Float.parseFloat(percent));
        }
    }
}
