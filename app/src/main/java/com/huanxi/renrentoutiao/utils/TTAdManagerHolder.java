package com.huanxi.renrentoutiao.utils;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdManagerFactory;
import com.huanxi.renrentoutiao.service.AppDownloadStatusListener;

/**
 * 可以用一个单例来保存TTAdManager实例
 */
public class TTAdManagerHolder {

    private static boolean sInit;

    public static TTAdManager getInstance(Context context) {
        TTAdManager ttAdManager = TTAdManagerFactory.getInstance(context);
        if (!sInit) {
            synchronized (TTAdManagerHolder.class) {
                if (!sInit) {
                    doInit(ttAdManager,context);
                    sInit = true;
                }
            }
        }
        return ttAdManager;
    }

    private static void doInit(TTAdManager ttAdManager,Context context) {
        ttAdManager.setAppId("5023855")
                .setName("云赚视频").setTitleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .setAllowShowNotifiFromSDK(true)
                .setAllowLandingPageShowWhenScreenLock(true)
                .openDebugMode()
                .setGlobalAppDownloadListener(new AppDownloadStatusListener(context))
                .setDirectDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G);
    }
}
