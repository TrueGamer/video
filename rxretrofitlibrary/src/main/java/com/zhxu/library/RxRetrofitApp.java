package com.zhxu.library;

import android.app.Application;

import com.zhxu.library.http.HttpManager;

/**
 * 全局APP
 */
public class RxRetrofitApp {

    private static Application application;
    private static boolean debug;
    private static String channel;
    private static String versionCode;


    public static void init(Application app){
        init(app,true);
    }

    public static void init(Application app, boolean debug){
        init(app,debug,null);
    }
    public static void init(Application app, boolean debug,Class  dialogClass){
        setApplication(app);
        setDebug(debug);
        HttpManager.getInstance().setProgressDialogClass(dialogClass);
    }
    public static void init(Application app, boolean debug,Class  dialogClass,String channel,String versionCode){
        setApplication(app);
        setDebug(debug);
        RxRetrofitApp.channel=channel;
        RxRetrofitApp.versionCode=versionCode;
        HttpManager.getInstance().setProgressDialogClass(dialogClass);
    }

    public static Application getApplication() {
        return application;
    }

    private  static void setApplication(Application application ) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }


    //===============这里是与项目相关的逻辑========================

    public static String mToken;

    public static  String getToken(){
        return mToken;
    }

    public static  void setToken(String token){
        mToken=token;
    }

    //=========这里是uid的一个==========

    public static String uid;

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        RxRetrofitApp.uid = uid;
    }

    /**
     * 这里是设置uid和token
     * @param token
     * @param uid
     */
    public static void setUidAndToken(String token,String uid){
        setToken(token);
        setUid(uid);
    }

    public static String getChannel(){
        return channel;
    }

    public static String getVersionCode(){
        return versionCode;
    }
}