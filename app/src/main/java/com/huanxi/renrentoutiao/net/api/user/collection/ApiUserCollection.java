package com.huanxi.renrentoutiao.net.api.user.collection;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/3.
 */

public class ApiUserCollection extends BaseApi<ResEmpty> {


    public static final String URLMD5="urlmd5";
    public static final String FROM_UID="from_uid";

    private final HashMap<String, String> mParamsMap;

    public ApiUserCollection(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).requestAlterCollection(mParamsMap);
    }
}
