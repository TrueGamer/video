package com.huanxi.renrentoutiao.net.api.video;

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
 * 这个操作就是阅读文章的操作；
 */

public class ApiStartPlayIssure extends BaseApi<ResEmpty>{

    HashMap<String,String> mParamsMap = new HashMap<String,String>();
    public static final String FROM_UID="from_uid";
    public static final String Type="type";

    public ApiStartPlayIssure(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).startPlayIssue(mParamsMap);
    }

    @Override
    public void handleException(Throwable e) {
    }
}
