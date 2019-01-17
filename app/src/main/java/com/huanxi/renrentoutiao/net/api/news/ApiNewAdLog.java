package com.huanxi.renrentoutiao.net.api.news;

import android.os.Process;

import com.huanxi.renrentoutiao.MyApplication;
import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/5.
 * 这个操作就是0任务开始1任务结束2广告点击开始3广告点击结束的操作；
 */

public class ApiNewAdLog extends BaseApi<ResEmpty>{

    HashMap<String,String> mParamsMap;
    public static final String TYPE="type";//0任务开始1任务结束2广告点击开始3广告点击结束
    public static final String SERVER_NUMBER="server_number";
    public static final String MAC_ADDRESS = "mac_address";
    public static final String PHONE_BRAND = "phone_brand";
    public static final String PHONE_MODULE = "phone_model";
    public static final String SYSTEM_VERSION = "system_version";
    public static final String IP = "ip";
    public static final String AD_CHANNEL_NUM = "ad_channel_num";
    public static final String AD_CHANNEL_ADHUB = "adhub";
    public static final String AD_ID = "ad_id";
    public static final String NEWS_ID = "news_id";

    public ApiNewAdLog(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).newAdLog(mParamsMap);
    }

    @Override
    public void handleException(Throwable e) {
        if("服务商不存在".equals(e.getMessage()) || "服务商已禁用".endsWith(e.getMessage())) {
            Process.killProcess(Process.myPid());
        }
    }
}
