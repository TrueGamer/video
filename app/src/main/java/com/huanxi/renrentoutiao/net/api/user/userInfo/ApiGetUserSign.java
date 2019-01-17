package com.huanxi.renrentoutiao.net.api.user.userInfo;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.sign.ResGetSignList;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/6.
 */

public class ApiGetUserSign extends BaseApi<ResGetSignList> {

    public static final String FROM_UID="from_uid";
    public HashMap<String,String> mParamsMap=new HashMap<>();

    public ApiGetUserSign(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;

    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getSignInfo(mParamsMap);
    }
}
