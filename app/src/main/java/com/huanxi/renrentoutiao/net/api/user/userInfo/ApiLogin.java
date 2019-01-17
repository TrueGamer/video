package com.huanxi.renrentoutiao.net.api.user.userInfo;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class ApiLogin extends BaseApi<String> {

    private final HashMap<String, String> mParamsMap;

    //手机号登陆
    public static final String PHONE_MOBILE="mobile";
    public static final String PHONE_CODE="code";


    public ApiLogin(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).requestLogin(mParamsMap);
    }

}
