package com.huanxi.renrentoutiao.net.api.user;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/9.
 */

public class ApiShareSuccess extends BaseApi<ResAward> {


    public static final String FROM_UID="from_uid";
    public static final String TYPE="type";
    public static final String URLMD5="urlmd5";

    public static final String TYPE_ARTICLE="1";
    public static final String TYPE_VIDEO="2";
    public static final String TYPE_INVITE="3";
    public static final String TYPE_EXPORE_INCOME="4";

    private final HashMap<String, String> mParamsMap;

    public ApiShareSuccess(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).shareSuccess(mParamsMap);
    }

    @Override
    public void handleException(Throwable e) {
    }
}
