package com.huanxi.renrentoutiao.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/1/22.
 *
 * 这里是我们加载图片的一个工具类；
 * 方便做统一的处理；
 */

public class ImageUtils {


    public static void loadImage(Activity activity, String url, ImageView view){

        if(activity == null || activity.isFinishing()|| TextUtils.isEmpty(url) || view ==null){
            return;
        }

        ColorDrawable colorDrawable = new ColorDrawable(activity.getResources().getColor(R.color.defaultImgColor));
        Glide.with(activity).load(url).dontAnimate().placeholder(colorDrawable).into(view);
    }
    public static void loadImage(Fragment fragment, String url, ImageView view){

        if(fragment == null|| fragment.isRemoving() || TextUtils.isEmpty(url) || view ==null){
            return;
        }

        ColorDrawable colorDrawable = new ColorDrawable(fragment.getActivity().getResources().getColor(R.color.defaultImgColor));
        Glide.with(fragment).load(url).dontAnimate().placeholder(colorDrawable).into(view);
    }

    public static void loadImage(Context context,String url,ImageView view){

        if(context == null || TextUtils.isEmpty(url) || view ==null){
            return;
        }

        ColorDrawable colorDrawable = new ColorDrawable(context.getResources().getColor(R.color.defaultImgColor));
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(colorDrawable)
                .into(view);
    }
}
