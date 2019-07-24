package com.huanxi.renrentoutiao;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.db.ta.sdk.TaSDK;
import com.huanxi.renrentoutiao.globle.ConstantAd;
import com.huanxi.renrentoutiao.globle.ConstantThreePart;
import com.huanxi.renrentoutiao.model.bean.UserBean;
import com.huanxi.renrentoutiao.net.bean.ResSplashAds;
import com.huanxi.renrentoutiao.service.AppDownloadStatusListener;
import com.huanxi.renrentoutiao.ui.dialog.LoadingDialog;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.huanxi.renrentoutiao.utils.SystemUtils;
import com.mob.MobSDK;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.zhxu.library.RxRetrofitApp;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;
import cn.tongdun.android.shell.db.utils.LogUtil;

/**
 * Created by Dinosa on 2018/1/18.
 */

public class MyApplication extends Application {

    private ResSplashAds mResSplashAds;
    public static Context mContext;
    private List<String> mAllPackageName;

    public static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initUM();

        initMob();

        initBugly();

        initNet();

        initJPush();

        initTa();

        mContext=this;

        initCsj();

        LogUtil.openLog();
    }

    private void initCsj() {
        TTAdSdk.init(mContext, new TTAdConfig.Builder()
                .appId("5023855")
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName(getString(R.string.app_name))
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .globalDownloadListener(new AppDownloadStatusListener(mContext)) //下载任务的全局监听
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                .supportMultiProcess(false)
                .build());
    }



    private void initTa() {

        TaSDK.init(this);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //这里添加点击事件；
        MultiActionsNotificationBuilder builder = new MultiActionsNotificationBuilder(this);
        JPushInterface.setPushNotificationBuilder(10, builder);
    }

    private void initNet() {


        RxRetrofitApp.init(this,true, LoadingDialog.class,SystemUtils.getAppMetaData(this)+"",SystemUtils.getVersionCode(this));

        //这里我们做一个相应的操作；
        String token = SharedPreferencesUtils.getInstance(this).getToken();
        UserBean userBean = SharedPreferencesUtils.getInstance(this).getUserBean();
        if (!TextUtils.isEmpty(token) && userBean!=null && !TextUtils.isEmpty(userBean.getUserid()) ) {
            RxRetrofitApp.setUidAndToken(token,userBean.getUserid());
        }
    }

    private void initBugly() {

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);

        String[] stringArray = getResources().getStringArray(R.array.channel);

        Integer appMetaData = SystemUtils.getAppMetaData(this);
        if(appMetaData>stringArray.length){
            //防止人人的是100
            appMetaData=stringArray.length;
        }
        strategy.setAppChannel(stringArray[appMetaData-1]);  //设置渠道
        strategy.setAppVersion(SystemUtils.getVersionName(this));      //App的版本

        ///CrashReport.initCrashReport(this, ConstantThreePart.BUGLY_APP_ID, true,strategy);
        Bugly.init(this, ConstantThreePart.BUGLY_APP_ID, true,strategy);
    }

    private void initMob() {

        MobSDK.init(this);
    }

    private void initUM() {

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);

    }


    public UserBean getUserBean(){

        return SharedPreferencesUtils.getInstance(this).getUserBean();
    }

    public void updateUser(UserBean userBean){
        //这里我们是需要更新token；
        SharedPreferencesUtils.getInstance(this).setUser(userBean);
    }

    public void updateTokenAndUid(String token,String uid){
        if(!TextUtils.isEmpty(token) && !TextUtils.isEmpty(uid)){
            RxRetrofitApp.setUidAndToken(token,uid);
            SharedPreferencesUtils.getInstance(this).saveToken(token);
        }
    }

    public void clearUser(){
        RxRetrofitApp.setToken("");
        RxRetrofitApp.setUid("");
        SharedPreferencesUtils.getInstance(this).clearToken();
        SharedPreferencesUtils.getInstance(this).clearUser();
    }

    /**
     * 绑定广告的返回值；
     */
    public void setResAds(ResSplashAds resSplashAds){
        mResSplashAds = resSplashAds;
    }

    public boolean isHasAds(){
        return mResSplashAds!=null;
    }


    /**
     * 返回所有的广告的操作；
     * @return
     */
    public ResSplashAds getResAds(){
        return mResSplashAds==null?new ResSplashAds():mResSplashAds;
    }


    public static Context getConstantContext(){
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
        // 安装tinker
        Beta.installTinker();
    }

    /**
     * 设置所有的包名的名字
     */
    public void setAllPackageName(List<String> allPackageName){
        mAllPackageName = allPackageName;
    }

    /**
     * 获取所有的包名
     * @return
     */
    public List<String> getAllPackageName(){
        return mAllPackageName;
    }


}
