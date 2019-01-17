package com.huanxi.renrentoutiao.ui.view.sign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dinosa on 2018/1/22.
 * 这里是签到的一个View；
 */

public class SignView extends View {

    private Paint mPaint;
    private Paint mSignGrayLineColor;
    private int mDays = 0;
    private Paint mSignYellowColor;
    private Paint mGrayTextPaint;
    private Paint mBlackPaint;


    public SignView(@NonNull Context context) {
        this(context, null);
    }

    public SignView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //这里我们先试一下将具体的签到的View的画出来；
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(UIUtils.dip2px(getContext(), 3));


        mSignYellowColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSignYellowColor.setColor(getContext().getResources().getColor(R.color.signed_color_bg));
        mSignYellowColor.setTextSize(UIUtils.sp2px(getContext(),12));
        mSignYellowColor.setStyle(Paint.Style.STROKE);
        mSignYellowColor.setStrokeWidth(UIUtils.dip2px(getContext(), 3));

        mSignGrayLineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSignGrayLineColor.setColor(getContext().getResources().getColor(R.color.home_item_divider_color));
        mSignGrayLineColor.setTextSize(UIUtils.sp2px(getContext(),12));
        mSignGrayLineColor.setStyle(Paint.Style.STROKE);
        mSignGrayLineColor.setStrokeWidth(UIUtils.dip2px(getContext(), 3));

        mGrayTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGrayTextPaint.setColor(getContext().getResources().getColor(R.color.sign_day_gray_text_color));
        mGrayTextPaint.setTextSize(UIUtils.sp2px(getContext(),12));

        mBlackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlackPaint.setColor(getContext().getResources().getColor(R.color.sign_day_black_text_color));
        mBlackPaint.setTextSize(UIUtils.sp2px(getContext(),14));
    }

    public int rectSize = 0;

    public int lineDistance = UIUtils.dip2px(getContext(), 10);

    //垂直方向上的距离；
    public int veticalDistance = UIUtils.dip2px(getContext(), 40);

    private int mHorizontalNumber = 7;
    private int mVeticalNumber = 3;

    private int paddingLeft = lineDistance * 2;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //上面表示能画多少个；
        rectSize = (getMeasuredWidth() - lineDistance * 10) / 7;

        Log.e("signView:veticalNumber", mVeticalNumber + "");
        Log.e("signView:number", mHorizontalNumber + "");

        //将我们需要的点画上去；
        List<RectF> pointFs = getDrawRectF();

        drawDefaultLine(canvas, pointFs);         //画默认的线；
        drawCheckedLine(canvas, pointFs, mDays);          //画选中的线；

        drawDefaultTextAndIcon(canvas, pointFs);   //画默认的一个文字和图标；
        drawCheckedTextAndIcon(canvas, pointFs, mDays);   //画选选中的文字和图标；
    }

    private void drawDefaultTextAndIcon(final Canvas canvas, List<RectF> pointFs) {
        mPaint.setColor(Color.BLACK);
        for (int i = 0; i < pointFs.size(); i++) {
            //将选中的条目颜色发生改变；
            final RectF rectF = pointFs.get(i);
            //这里我们是需要画具体的内容的
            drawShowIcon(canvas,rectF,i);

            drawDayText(canvas,rectF,i);
        }
    }

    /**
     * 这里是画文字，day天数之类的；
     * @param canvas
     * @param rectF
     * @param day
     */
    private void drawDayText(Canvas canvas, RectF rectF,int day) {
        String dayStr=(day+1)+"天";

        Rect rect = new Rect();
        mGrayTextPaint.getTextBounds(dayStr,0,dayStr.length(),rect);
        float startX=rectF.left+(rectF.width()-rect.width())/2;
        float startY=(rectF.bottom+rect.height()+UIUtils.dip2px(getContext(),3));

        canvas.drawText(dayStr,startX,startY,mGrayTextPaint);
    }

    /**
     * 这里是对应的图标；
     * @param canvas
     * @param rectF
     */
    private void drawShowIcon(Canvas canvas,RectF rectF,int day) {
        int realDay=day+1;
        if(realDay==14 || realDay==21){
            Bitmap bmp = drawableToBitmap(getResources().getDrawable(R.drawable.shape_rectangle_light_gray_bg));
            canvas.drawBitmap(bmp, rectF.left, rectF.top, mBlackPaint);

            String realDayStr="+"+realDay;
            Rect rect = new Rect();
            //画文字；
            mBlackPaint.getTextBounds(realDayStr,0,realDayStr.length(),rect);
            float startX=rectF.left+(rectF.width()-rect.width())/2;
            float startY=rectF.top+(rectF.height()+rect.height())/2;
            canvas.drawText(realDayStr,startX,startY,mBlackPaint);
        }else{
            Bitmap bmp = drawableToBitmap(getResources().getDrawable(R.drawable.shape_unsign_bg));
            //这里是画背景图片
            canvas.drawBitmap(bmp, rectF.left, rectF.top, mSignGrayLineColor);
            //这里画背景图片上面的图案
            Drawable drawable = getResources().getDrawable(R.drawable.icon_gold_gray);
            int width = rectSize*3/4;
            Bitmap bitmap = drawableToBitmap(drawable,width,width);

            canvas.drawBitmap(bitmap,rectF.left+width/6,rectF.top+width/6,mSignGrayLineColor);
        }
    }

    public Bitmap drawableToBitmap(Drawable drawable){
        return drawableToBitmap(drawable,rectSize,rectSize);
    }

    public  Bitmap drawableToBitmap(Drawable drawable,int width,int height) {
                 // 取 drawable 的长宽
                 //int w = drawable.getIntrinsicWidth();
                 //int h = drawable.getIntrinsicHeight();

                int w=width;
                int h=height;

                 // 取 drawable 的颜色格式
                 Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                         : Bitmap.Config.RGB_565;
                 // 建立对应 bitmap
                 Bitmap bitmap = Bitmap.createBitmap(w, h, config);
                 // 建立对应 bitmap 的画布
                 Canvas canvas = new Canvas(bitmap);
                 drawable.setBounds(0, 0, w, h);
                 // 把 drawable 内容画到画布中
                 drawable.draw(canvas);
                 return bitmap;
             }

    /**
     * 获取我们每一个地方需要绘制的位置；
     *
     * @return
     */
    private List<RectF> getDrawRectF() {
        //这里要做的一个操作就是获取具体的矩形选中框；
        ArrayList<RectF> pointFs = new ArrayList<>();

        int curTop = lineDistance;
        int curLeft = paddingLeft;

        for (int i = 0; i < mVeticalNumber; i++) {

            //这里表示遍历每一行；

            if (i % 2 == 0) {
                //这里表偶数行；
                for (int j = 0; j < mHorizontalNumber; j++) {
                    //这里表示画每一行的某一个小块元素；
                    RectF rectF = new RectF(curLeft, curTop, curLeft + rectSize, curTop + rectSize);
                    pointFs.add(rectF);
                    //20的距离表示线；
                    curLeft += lineDistance + rectSize;
                }
            } else {
                //这里表示奇数行；
                curLeft = (mHorizontalNumber - 1) * (lineDistance + rectSize) + paddingLeft;
                for (int j = mHorizontalNumber; j > 0; j--) {

                    RectF rectF = new RectF(curLeft, curTop, curLeft + rectSize, curTop + rectSize);
                    pointFs.add(rectF);

                    //这里表示画每一行的某一个小块元素；
                    curLeft -= lineDistance + rectSize;
                }
            }
            curTop += rectSize + veticalDistance;
            curLeft = paddingLeft;
        }
        return pointFs;
    }

    public void setCheckedDays(int days) {
        if(days>21){
            return;
        }
        mDays = days;
        //这里我们要做的一个操作就是刷新页面
        invalidate();
    }

    public int getCheckedDays() {
        return mDays;
    }

    /**
     * 画选中的样式
     *
     * @param canvas
     * @param selectRects
     * @param checkedNumber 选中的了多少天
     */
    private void drawCheckedLine(Canvas canvas, List<RectF> selectRects, int checkedNumber) {

        for (int i = 0; i < checkedNumber - 1; i++) {
            if (i == 6) {
                drawRightLine(canvas, selectRects.get(i), selectRects.get(i + 1),mSignYellowColor);

            } else if (i == 13) {
                drawLeftLine(canvas, selectRects.get(i), selectRects.get(i + 1),mSignYellowColor);

            } else {
                drawDefaultLine(canvas, selectRects.get(i), selectRects.get(i + 1),mSignYellowColor);
            }
        }
    }

    /**
     * 这里是需要画选中的样式；
     *
     * @param canvas
     * @param selectRects
     * @param checkedNumber 选中了多少天；
     */
    private void drawCheckedTextAndIcon(Canvas canvas, List<RectF> selectRects, int checkedNumber) {


        for (int i = 0; i < checkedNumber; i++) {
            //将选中的条目颜色发生改变；
            RectF rectF = selectRects.get(i);
            //canvas.drawRect(rectF, mPaint);
            Bitmap bmp = drawableToBitmap(getResources().getDrawable(R.drawable.shape_sign_bg));

            //Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.shape_unsign_bg);
            canvas.drawBitmap(bmp, rectF.left, rectF.top, mSignGrayLineColor);
        }
    }

    //这里画线，需要根据奇偶行来进行一个化线

    private void drawDefaultLine(Canvas canvas, List<RectF> pointFs) {

        for (int i = 0; i < pointFs.size() - 1; i++) {

            if (i == 6) {
                //这里表示右边的线的；
                drawRightLine(canvas, pointFs.get(i), pointFs.get(i + 1),mSignGrayLineColor);

            } else if (i == 13) {
                drawLeftLine(canvas, pointFs.get(i), pointFs.get(i + 1),mSignGrayLineColor);
            } else {
                //这里式默认的画线的方式；
                drawDefaultLine(canvas, pointFs.get(i), pointFs.get(i + 1),mSignGrayLineColor);
            }

        }

    }

    private void drawDefaultLine(Canvas canvas, RectF one, RectF next,Paint paint) {
        canvas.drawLine(one.left + one.width() / 2, one.top + one.height() / 2, next.left + next.width() / 2, next.top + next.height() / 2, paint);
    }

    private void drawLeftLine(Canvas canvas, RectF one, RectF next,Paint paint) {

        PointF leftTop = new PointF(one.left - lineDistance, one.top + one.height() / 2);
        PointF leftBottom = new PointF(next.left - lineDistance, next.top + next.height() / 2);

        PointF rightTop = new PointF(one.left+one.width()/2, one.top + one.height() / 2);
        PointF rightBottom = new PointF(next.left+one.width()/2, next.top + next.height() / 2);

        Path path = new Path();
        path.moveTo(rightTop.x, rightTop.y);
        path.lineTo(leftTop.x, leftTop.y);
        path.lineTo(leftBottom.x, leftBottom.y);
        path.lineTo(rightBottom.x, rightBottom.y);
        canvas.drawPath(path, paint);
    }

    private void drawRightLine(Canvas canvas, RectF one, RectF next,Paint paint) {

        PointF leftTop = new PointF(one.left, one.top + one.height() / 2);
        PointF rightTop = new PointF(one.right + lineDistance, one.top + one.height() / 2);

        PointF leftBottom = new PointF(next.left, next.top + next.height() / 2);
        PointF rightBottom = new PointF(next.right + lineDistance, next.top + next.height() / 2);

        Path path = new Path();
        path.moveTo(leftTop.x, leftTop.y);
        path.lineTo(rightTop.x, rightTop.y);
        path.lineTo(rightBottom.x, rightBottom.y);
        path.lineTo(leftBottom.x, leftBottom.y);

        canvas.drawPath(path, paint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //这里我们是需要让宽固定的；

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if(mode == MeasureSpec.EXACTLY){
            int size = MeasureSpec.getSize(widthMeasureSpec);
            //
            int rectSize = (size - lineDistance * 10) / 7;

            int height=rectSize*3+veticalDistance*3+UIUtils.dip2px(getContext(),3);
            int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    getDefaultSize(getSuggestedMinimumHeight(), heightSpec));
        }
    }
}
