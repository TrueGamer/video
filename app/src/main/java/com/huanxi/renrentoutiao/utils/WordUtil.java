package com.huanxi.renrentoutiao.utils;

import android.content.res.Resources;

import com.huanxi.renrentoutiao.MyApplication;

/**
 * Created by cxf on 2017/10/10.
 * 获取string.xml中的字
 */

public class WordUtil {

    private static Resources sResources;

    static {
        sResources = MyApplication.sInstance.getResources();
    }

    public static String getString(int res) {
        return sResources.getString(res);
    }
}
