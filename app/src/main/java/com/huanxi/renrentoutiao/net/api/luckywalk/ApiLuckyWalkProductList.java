package com.huanxi.renrentoutiao.net.api.luckywalk;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.luckywalk.ResLuckwalkProductBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/4/19.
 */

public class ApiLuckyWalkProductList extends BaseApi<List<ResLuckwalkProductBean>> {

    public ApiLuckyWalkProductList(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getLuckyProductList();
    }
}
