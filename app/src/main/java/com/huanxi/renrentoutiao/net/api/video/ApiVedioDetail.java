package com.huanxi.renrentoutiao.net.api.video;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.video.ResVedioSource;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.api.BaseResultEntity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/25.
 */

public class ApiVedioDetail extends BaseApi<ResVedioSource> {

    private final HashMap<String, String> mParamsMap;

    public ApiVedioDetail(HttpOnNextListener listener, HashMap<String,String> paramsMap, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getVedioSourceDetail(mParamsMap);
    }

    @Override
    public ResVedioSource call(BaseResultEntity<ResVedioSource> httpResult) {
        return httpResult.getData();
    }
}
