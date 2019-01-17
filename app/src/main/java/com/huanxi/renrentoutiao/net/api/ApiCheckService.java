package com.huanxi.renrentoutiao.net.api;

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

public class ApiCheckService extends BaseApi<String>{

    HashMap<String,String> mParamsMap;
    public static final String SERVER_NUMBER="server_number";

    public ApiCheckService(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).checkService(mParamsMap);
    }

    @Override
    public void handleException(Throwable e) {
    }
}
