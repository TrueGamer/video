package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by andy on 2017/6/6.
 * 这里的话，我们是不支持无平滑过渡，无平滑过渡就会产生一个bug，导致出现问题；
 */
public class VeticalDrawerLayout extends FrameLayout {

    ViewDragHelper mDragHelper;
    private ViewGroup mContentLayout;
    private int mContentHeight;
    private boolean mIsCanTouch=true;


    public VeticalDrawerLayout(Context context) {
        this(context,null);
    }

    public VeticalDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VeticalDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this,callBack);
        setBackground(new ColorDrawable(0xEE999999));
    }

    private int mCurrentTop;
    //这里话，我们要做的一个操作就复杂了，由之前的水平，我们将换成垂直方向上的；
    ViewDragHelper.Callback callBack = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //如果是外边的布局我们就尝试捕获；
            return child == mContentLayout;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mContentHeight;
        }

        //这里表示将要移动但是还没有移动的情况；
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //这里的话， 我们要做的一个操作就是对移动的位置进行一个修正；
            if(child==mContentLayout){
                top=fixTop(top);
            }
            return top;
        }

        //这个方法里面我们要做的一个操作就是联动的实现；
        // 这里我们通过重新布局的方式来实现的，原因在于我们可以防止由于其他的因素影响导致，onLayout重新布局出现问题；
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            mCurrentTop =top ;
            //计算移动的百分比；
            float percent = top * 1.0f / mContentHeight;
            //根据这个百分比我们将实现相应的动画；
            startAnimation(percent);
            dispachDragEvent(top);
            invalidate();
        }

        //这里的话，我们是需要处理手指松动的动画的逻辑；
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            System.out.println("yvel: "+yvel+ "mContentHeight:"+ mContentHeight +"==top:"+mContentLayout.getTop());
            if(mContentLayout.getTop()<0){

            } else if(yvel==0 && mContentLayout.getTop()> mContentHeight /4){
                //这样的话，我们也是认为其是打开的；
                open();
            }else if(yvel>2000){
                //速度大于2000才让其打开，因为防止误操作,这里的话表示的是打开的；
                open();
            }else{
                //这里的话表示是关闭；
                close();
            }
        }
    };

    /**
     * 这里我们要根据移动的这个修改其VeticalDrawerLayout的背景颜色
     * @param percent
     */
    private void startAnimation(float percent) {

        Object evaluate = colorEvaluate(percent, 0xEE999999 , Color.TRANSPARENT);
        setBackground(new ColorDrawable((Integer) evaluate));

        System.out.println("veticalDrawerLayout:percent: "+percent+" color: "+evaluate);
    }

    public Object colorEvaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }


    //这里的话，我们是需要关闭的；
    public void close() {

        smoothClose(true);
        isClose=false;
    }


    //打开的；
    public void open() {
        smoothOpen(true);
        isClose=true;
    }

    private void smoothOpen(boolean isSmooth) {

        if(isSmooth){
            if(mDragHelper.smoothSlideViewTo(mContentLayout, 0, mContentHeight)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else{
            //暂时不做；
        }
    }

    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void smoothClose(boolean isSmooth) {

        if (isSmooth) {

            if(mDragHelper.smoothSlideViewTo(mContentLayout, 0, 0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }

        }else{
            //暂时不做
        }

    }

    public OnDragStatusChange mOnDragStatusChange;

    public void setOnDragStatusChange(OnDragStatusChange onDragStatusChange) {
        mOnDragStatusChange = onDragStatusChange;
    }

    //暴露接口；
    public interface OnDragStatusChange{
        void onOpen();
        void onClose();
        void OnDrag(float percent);
    }

    public void toggle(){
        if(mCurStaus== Status.close){
            open();
        }else{
            close();
        }
    }


    public enum Status{
        open,close,dragging
    }

    public Status mCurStaus= Status.close;

    //这里我将事件暴露出去，供别人使用；这里的话，我们应该将进度的百分比给弄出去，
    // 这样的话，如果有其他的动画需要联动的话，我们我们也是可以实现联动的效果；
    private void dispachDragEvent(int fixTop) {
        //这里的话，我们获取得到百分比；
        float percent = fixTop * 1.0f / mContentHeight;
        if (mOnDragStatusChange == null) {
            return;
        }
        //这里如果不设置状态的话，我们在外边是没有办法点击打开的；

        mCurStaus= updateDragStatus(percent);
        if(mCurStaus.equals(Status.open)){
            mOnDragStatusChange.onOpen();
        }else if(mCurStaus.equals(Status.close)){
            mOnDragStatusChange.onClose();
        }else {
            mOnDragStatusChange.OnDrag(percent);
        }
    }

    private Status updateDragStatus(float percent) {
        //这里我们要实现相应的功能；
        if(percent==0){
            return Status.close;
        }else if(percent==1){
            return Status.open;
        }
        return Status.dragging;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }


    /**
     * 这里我们对高度进行一个修正，
     * @param top
     * @return
     */
    private int fixTop(int top) {
        //这里的话，我们要对其进行一个修正；
        if(top<5){
            top=0;
        }else if(top> mContentHeight){
            top= mContentHeight;
        }
        return top;
    }

    /**
     * 这里的话，我们将进行的一个操作就是获取子布局；
     * 当我们拖动外边的布局的时候，里面的布局其实是会发生变化的；
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentLayout = ((ViewGroup) getChildAt(1));
    }

    /**
     * 这里我们是可以获取布局的相应的高度的；我们通过滑动布局的相应的高度；
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这个是里面的布局的一个高度；这里-2的原因，在我的手机上，有时候会出现空隙的现象，

        if (mContentLayout != null) {
            mContentHeight = mContentLayout.getMeasuredHeight() + 20;
            mCurrentTop=mContentHeight;
        }
    }



    float downX;
    float downY;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //这里我们做一个判断如果是水平的滑动我们是不处理的；
        float curX=0;
        float curY=0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
                downY = ev.getRawY();
            case MotionEvent.ACTION_MOVE:

                curX = ev.getRawX();
                curY = ev.getRawY();

                float dx = Math.abs(curX - downX);
                float dy = Math.abs(curY - downY);

                System.out.println("移动的距离：" + "dx： " + dx + " dy: " + dy);
                //水平方向的滑动近距离大于竖直方向的的时候，我们是让ViewPager自己处理点击事件；
                if (dx > dy) {
                    return false;
                }
                break;
            default:
                break;
        }
        boolean b = mDragHelper.shouldInterceptTouchEvent(ev);
        if(!mIsCanTouch){
            b = false;
        }
        System.out.println("VeticalDrawerLayout:onInterceptTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将事件交给ViewDragHelper来处理；
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mContentLayout.layout(0, mCurrentTop,
                mContentLayout.getMeasuredWidth(),
                mCurrentTop + mContentHeight);
    }


    public void setCanTouch(boolean isCanTouch){
        mIsCanTouch = isCanTouch;
    }

    public boolean isClose=true;

    /**
     * 这里判断是否是展开的；
     * @return
     */
    public boolean isClose(){

        return isClose;
    }

}

