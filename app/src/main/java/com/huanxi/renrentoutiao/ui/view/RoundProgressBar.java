package com.huanxi.renrentoutiao.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.UIUtils;

/**
 * 自定义环形进度条
 * @author qiulong
 *
 */
@SuppressLint("DrawAllocation")
public class RoundProgressBar extends View {

    /** 默认圆环的画笔 */
    private Paint defaultRoundPaint;
    /** 圆环进度的画笔 */
    private Paint progresRoundPaint;
    /** 百分比字体的画笔 */
    private Paint percenTextPaint;
    /** 电量字体的画笔 */
    private Paint electriTextPaint;
    /**
     * 中间图标
     */
    private Paint iconPaint;

    /** 默认圆环的背景颜色 */
    private int defaultRoundColor;
    /** 圆环进度的颜色 */
    private int roundProgressColor;
    /** 百分比字体颜色 */
    private int percenTextColor;
    /** 电量字体颜色 */
    private int electriTextColor;

    /** 百分比字体大小 */
    private float percenTextSize;
    /** 电量字体大小 */
    private float electriTextSize;
    /** 电量文字内容*/
    private String electriText;
    /** 是否显示电量文字(默认是不显示)*/
    private boolean showElectriText = false;

    /** 圆环进度的宽度 */
    private float roundWidth;
    /** 圆环进度的最大值，默认100 */
    private int max;
    /** 当前进度值 */
    private int progress;
    /** 圆弧的外轮廓矩形区域 */
    private RectF oval;

    /** 充电图标轨迹 */
    private Path chargingPath;
    /** 充电图标的颜色 */
    private int chargingColor;
    /** 是否显示充电图标(默认是不显示) */
    private boolean showChargingIcon = false;
    private Bitmap bmpClose;
    private Bitmap bmpOpen;


    public RoundProgressBar(Context context) {
        this(context, null);
    }


    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //加载xml自定义属性
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        defaultRoundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_defaultRoundColor, Color.BLACK);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
        percenTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_percenTextColor, Color.WHITE);
        percenTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_percenTextSize, 38);
        //充电文字内容
        electriText = "进度";
        electriTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_electriTextColor, Color.WHITE);
        electriTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_electriTextSize, 18);
        chargingColor = mTypedArray.getColor(R.styleable.RoundProgressBar_chargingColor, Color.GREEN);
        UIUtils.dip2px(context, roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 15));
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        mTypedArray.recycle();//使用缓存

        init();
    }


    /**
     * 初始化
     */
    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        defaultRoundPaint = new Paint();
        defaultRoundPaint.setAntiAlias(true);
        defaultRoundPaint.setColor(defaultRoundColor);
        defaultRoundPaint.setStyle(Paint.Style.STROKE);
        defaultRoundPaint.setStrokeWidth(roundWidth);

        progresRoundPaint = new Paint();
        progresRoundPaint.setAntiAlias(true);
        progresRoundPaint.setColor(roundProgressColor);
        progresRoundPaint.setStyle(Paint.Style.STROKE);
        progresRoundPaint.setStrokeWidth(roundWidth);

        percenTextPaint = new Paint();
        percenTextPaint.setAntiAlias(true);
        percenTextPaint.setColor(percenTextColor);
        percenTextPaint.setStyle(Style.FILL);
        percenTextPaint.setTextSize(percenTextSize);

        electriTextPaint = new Paint();
        electriTextPaint.setAntiAlias(true);
        electriTextPaint.setColor(electriTextColor);
        electriTextPaint.setStyle(Style.FILL);
        electriTextPaint.setTextSize(electriTextSize);

        iconPaint = new Paint();
        iconPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算中心点
        int centre = getWidth() / 2;
        //计算半径
        int radius = (int) (centre - roundWidth / 2);
        //绘制默认环形进度
        canvas.drawCircle(centre, centre, radius, defaultRoundPaint);

        //绘制环形进度
        if (oval == null) {
            oval = new RectF(centre - radius, centre - radius,
                    centre + radius, centre + radius);
        }
        canvas.drawArc(oval, -90, 360 * progress / max, false, progresRoundPaint);

        //先判断是否是充电
        if(showChargingIcon){
            //绘制正在充电图标
            canvas.save();
            canvas.clipPath(drawPath(getWidth()));// 先画好轨迹
            canvas.drawColor(chargingColor);
            canvas.restore();

//            float imageLeft = getWidth()/2 - bmpClose.getWidth()/2;
//            float imageTop = getHeight()/2 - bmpClose.getHeight()/2;
//            canvas.drawBitmap(bmpClose ,imageLeft ,imageTop , iconPaint);
        }else{
            int Offset = 0;
            if(showElectriText){
                Offset = 30;
            }
            //绘制进度值文字
            int percent = progress * 100 / max;
            float percenTextWidth = percenTextPaint.measureText(percent + "%");
            canvas.drawText(percent + "%", centre - percenTextWidth / 2,
                    centre + (percenTextSize / 2) - Offset, percenTextPaint);

            if(showElectriText){
                //绘制电量文字
                float electriTextWidth = electriTextPaint.measureText(electriText);
                canvas.drawText(electriText, centre - electriTextWidth / 2,
                        centre + (electriTextSize / 2) + Offset, electriTextPaint);
            }
        }
    }


    /**
     * 绘制充电图标轨迹
     * @param width
     */
    private Path drawPath(int width){
        int avg = width/12;
        if(chargingPath == null){
            chargingPath = new Path();
        }
        chargingPath.moveTo(avg * 7, avg * 2);
        chargingPath.lineTo((avg * 6)+(avg/2), avg * 5);
        chargingPath.lineTo(avg * 8, avg * 5);
        chargingPath.lineTo(avg * 5, avg * 10);
        chargingPath.lineTo((avg * 5)+(avg/2), avg * 7);
        chargingPath.lineTo(avg * 4, avg * 7);
        chargingPath.close();
        return chargingPath;
    }


    /**
     * 设置圆形进度的最大值
     * @param max
     */
    public void setMax(int max) {
        if(max < 0){
            max = 100;//默认为100
        }
        this.max = max;
    }


    /**
     * 设置进度值
     * @param progress
     */
    public void setProgress(int progress) {
        if(progress < 0){
            progress = 0;
        }
        if(progress > max){
            progress = max;
        }
        if(progress <= max){
            this.progress = progress;
            postInvalidate();
        }
    }

    /**
     * 获取当前进度
     * @return
     */
    public int getProgress() {

        return this.progress;
    }


    /**
     * 设置圆环进度的宽度
     * @param roundWidth
     */
    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }


    /**
     * 设置进度的背景颜色(底色)
     * @param cricleColor
     */
    public void setDefaultRoundColor(int cricleColor) {
        this.defaultRoundColor = cricleColor;
    }


    /**
     * 设置进度的颜色
     * @param roundProgressColor
     */
    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }


    /**
     * 设置百分比字体的颜色
     * @param percenTextColor
     */
    public void setPercenTextColor(int percenTextColor) {
        this.percenTextColor = percenTextColor;
    }


    /**
     * 设置百分比字体大小
     * @param percenTextSize
     */
    public void setPercenTextSize(float percenTextSize) {
        this.percenTextSize = percenTextSize;
    }


    /**
     * 设置是否显示电量文字
     * @param showElectriText
     */
    public void setShowElectriText(boolean showElectriText){
        this.showElectriText = showElectriText;
        postInvalidate();
    }


    /**
     * 设置是否显示充电图标
     * @param showChargingIcon
     */
    public void setShowChargingIcon(boolean showChargingIcon){
        this.showChargingIcon = showChargingIcon;
        postInvalidate();
    }
}
