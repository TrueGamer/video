package com.huanxi.renrentoutiao.net.api;

import com.huanxi.renrentoutiao.model.bean.AdsBean;
import com.huanxi.renrentoutiao.net.ApiServices;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/3/28.
 */

public class ApiAds extends BaseApi<AdsBean> {


    HashMap<String,String> mParamsMap=new HashMap<>();

    public ApiAds(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).geTads();
    }
}
