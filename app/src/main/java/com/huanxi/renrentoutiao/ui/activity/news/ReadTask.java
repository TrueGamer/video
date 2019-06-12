package com.huanxi.renrentoutiao.ui.activity.news;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yanzhang on 2018/7/16.
 */
public class ReadTask {
    private static final int TIME_TOTAL = 32;//s
    private int mTime = 1;//5s
    private Disposable mDisposable;
    private boolean mScrollEnd = false;
    private boolean mHasFinished;
    private TimeListener mListener;
    private boolean mTimeOver = false;

    public void scrollEnd() {
        mScrollEnd = true;
        finish();
    }

    public float getProgress() {
        return mTime * 1f / TIME_TOTAL * 0.96f;
    }

    private static class SingletonHolder {
        private final static ReadTask instance = new ReadTask();
    }

    public static ReadTask getInstance() {
        return SingletonHolder.instance;
    }

    public void reset() {
        if (mListener != null) {
            //listener有值说明没有调用stop就调用了reset 说明上一个详情页没有销毁 只有所有的详情页都销毁了 才允许reset
            return;
        }
        mTime = 1;
        mScrollEnd = false;
        mHasFinished = false;
        mTimeOver = false;
    }


    public void start(TimeListener listener) {
        if (mTime >= TIME_TOTAL) {
            return;
        }
        mListener = listener;
        if (mDisposable == null || mDisposable.isDisposed()) {
            mDisposable = Flowable.intervalRange(0,
                    TIME_TOTAL,
                    0,
                    1,
                    TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            mTime = aLong.intValue();
                            Log.i("info", "mTime = " + aLong);
                            if (mListener != null) {
                                mListener.onTick(getProgress());
                            }
//                            if (mTime % 5 == 0 && mTime != TIME_TOTAL) {
//                                mTime++;
//                                mDisposable.dispose();
//                            }
                        }
                    })
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            Log.i("info", "mTime over");
                            mTimeOver = true;
                            finish();

                        }
                    })
                    .subscribe();
        }
    }

    private void finish() {
        if (mListener != null && !mHasFinished && mTimeOver) {
            if (!mScrollEnd) {
                return;
            }
            mHasFinished = true;
            mListener.onFinish();
        }
    }


    public void stop() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mListener = null;
    }

    public interface TimeListener {
        void onTick(float v);

        void onFinish();
    }
}
