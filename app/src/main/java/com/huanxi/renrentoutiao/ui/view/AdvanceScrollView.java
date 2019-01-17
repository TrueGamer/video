package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Dinosa on 2018/1/27.
 * 这里是一个ScrollView的具体的实现操作；
 * 我们的这个ScrollView可以设置滚动的监听操作；
 */

public class AdvanceScrollView extends ScrollView {


    private ScrollViewListener scrollViewListener;

    public AdvanceScrollView(Context context) {
        super(context);
    }

    public AdvanceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvanceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public interface ScrollViewListener {

        void onScrollChanged(AdvanceScrollView scrollView, int x, int y, int oldx, int oldy);

    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
    //重写滚动方法
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
