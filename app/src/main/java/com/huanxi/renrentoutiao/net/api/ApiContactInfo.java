package com.huanxi.renrentoutiao.net.api;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/8.
 */

public class ApiContactInfo extends BaseApi<List<String>> {

    public ApiContactInfo(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getContactInfo();
    }
}
