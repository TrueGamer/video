package com.huanxi.renrentoutiao.net.api.video;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.api.BaseParamsApi;
import com.huanxi.renrentoutiao.net.bean.video.ResVideoList;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/25.
 */

public class ApiVedioListDetail extends BaseParamsApi<ResVideoList> {


    public static String CATEGORY="category";
    public static String LATITUDE="latitude";
    public static String LONGITUDE="longitude";
    public static String CITY="city";

    //category=video&latitude=11&longitude=100&city=%E5%8C%97%E4%BA%AC

    public ApiVedioListDetail(HttpOnNextListener listener, HashMap<String,String> paramsMap, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity,paramsMap);
        mParamsMap.put(LATITUDE,"11");
        mParamsMap.put(LONGITUDE,"100");
        mParamsMap.put(CITY,"%E5%8C%97%E4%BA%AC");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getVedioListDetail(mParamsMap);
    }


}
