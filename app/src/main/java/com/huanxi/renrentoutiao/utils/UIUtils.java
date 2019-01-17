package com.huanxi.renrentoutiao.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/1/22.
 */

public class UIUtils {

    public static Toast sToast;

    public static void toast(Context context,String text){

        if(sToast == null){
            sToast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }
        sToast.setText(text);
        sToast.show();
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 这里是将sp转换为px
     * @return
     */
    public static int sp2px(Context context, float spValue){

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,

                spValue, context.getResources().getDisplayMetrics());
    }


    /**
     * @param mContext Context
     * @param time     倒计时的时间
     * @param btn      倒计时的控件
     */
    public static void countDowm(final Context mContext, int time,
                                 final TextView btn, final String tvText) {

        CountDownTimer timer = new CountDownTimer(time * 600, 1000) {
            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onTick(long millisUntilFinished) {
                btn.setText("还剩" + millisUntilFinished / 600 + "秒");
                btn.setClickable(false);
            }

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onFinish() {
                btn.setText(tvText);
                btn.setClickable(true);
            }
        };
        timer.start();
    }

    /**
     * 获取屏幕的宽度；
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private static final int MIN_CLICK_DELAY_TIME = 3000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = curClickTime;
            flag = true;
        }
        return flag;
    }


    /**
     * 提现按钮限制点击
     *
     * @return
     */
    public static boolean isFastTXClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
