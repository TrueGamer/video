package com.huanxi.renrentoutiao.net.api;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/27.
 */

public class ApiGetInviteAward extends BaseApi<ResAward> {

    private final HashMap<String, String> mParamsMap;
    public static final String FROM_UID="from_uid";

    public ApiGetInviteAward(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).requestInviteAward(mParamsMap);
    }
}
