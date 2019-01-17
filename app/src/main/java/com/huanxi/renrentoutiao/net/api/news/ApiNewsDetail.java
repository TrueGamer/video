package com.huanxi.renrentoutiao.net.api.news;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.news.ResNewsDetailBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/27.
 *
 * 这里我们
 */

public class ApiNewsDetail extends BaseApi<ResNewsDetailBean>{

    HashMap<String,String> mParamsMap = new HashMap<String,String>();

    public static final String URL="url";
    public static final String FROM_UID="from_uid";


    public ApiNewsDetail(HttpOnNextListener listener,HashMap<String,String> paramsMap, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getNewsDetail(mParamsMap);
    }


}
