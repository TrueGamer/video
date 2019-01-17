package com.huanxi.renrentoutiao.ui.activity.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Dinosa on 2018/3/5.
 */

public class MyNestedScrollParent extends LinearLayout implements NestedScrollingParent {


    public MyNestedScrollParent(Context context) {
        this(context,null);
    }

    public MyNestedScrollParent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNestedScrollParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

    }

    /**
     *
     * @param target target就是内部嵌套的View
     * @param dx dx是x方向的滑动偏移
     * @param dy dy是y方向的滑动偏移
     * @param consumed consumed的含义是消耗，它是一个长度是2的int数组，consumed[0]代表x轴，consumed[1]代表y轴
     *                 就是说子控件滑动时的偏移父控件想要消耗多少。例如我们想要把所有的y轴的滑动事件都消耗掉
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }
}
