package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dinosa on 2018/2/26.
 */

public class ReadProgressView extends View {

    public ReadProgressView(Context context) {
        this(context,null);
    }

    public ReadProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ReadProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //这里我们将画一个进度条；
    }
}
