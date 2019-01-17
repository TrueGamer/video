package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by andy on 2017/6/23.
 * 这里是需要配合VetivalDrawerLayout一起使用的一个组件
 */

public class MyScrollerView extends ScrollView {
    private int mTop;

    public MyScrollerView(Context context) {
        this(context,null);
    }

    public MyScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //这里的话，ScrollView的逻辑是什么样的啊；
/*    分情况讨论：
    1、Top==0的时候，ScrollerView是不处理的
    2、top>0的时候
        向上滚动：
            1、如果抽屉没有打开，我们是可以继续向上滚动的；
            2、抽屉打开了（我们可以用getParent().getTop来判断），我们是不能拦截的；
        向下滚动：
            1、自己处理
    */

    float downX;
    float downY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //这里我们通过手势来进行一个判断；

        float curX=0;
        float curY=0;
        System.out.println("MyScrollerView: 进来了");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX=ev.getRawX();
                downY=ev.getRawY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:

                curX=ev.getRawX();
                curY=ev.getRawY();

                float dx = Math.abs(curX - downX) ;
                float dy = Math.abs(curY - downY);
                if(curY-downY<0){
                    //这里表示向上滑动
                    //还要判断一下抽屉是否打开了
                    //System.out.println( "top"+();
                    System.out.println("MyScrollerView: top: "+ ((FrameLayout) getParent()).getTop());
                    if (((FrameLayout) getParent()).getTop()==0) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        System.out.println("MyScrollerView: top:==0 ");
                    }else{
                        getParent().requestDisallowInterceptTouchEvent(false);
                        System.out.println("MyScrollerView: top:!=0 ");
                    }
                }else{
                    //这里表示向下滑动；
                    if(mTop>0){
                        getParent().requestDisallowInterceptTouchEvent(true);
                        System.out.println("MyScrollerView: mTop>0");
                    }else{
                        //小于等于0
                        getParent().requestDisallowInterceptTouchEvent(false);
                        System.out.println("MyScrollerView: mTop<0");
                    }

                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        System.out.println("MyScrollerView:onInterceptTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        System.out.println("MyScrollerView:onTouchEvent: "+b);
        return b;
    }

    @Override
    protected void onScrollChanged(int l, int top, int oldl, int oldTop) {

        mTop = top;
        System.out.println("MyScrollerView: 滚动的距离:"+top);
        super.onScrollChanged(l, top, oldl, oldTop);
    }

}
