package com.huanxi.renrentoutiao.net.api.user;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.ResWallettotalBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/8.
 */

public class ApiUserWallettotal extends BaseApi<ResWallettotalBean> {

    private final HashMap<String, String> mParamsHashMap;

    public static final String FROM_UID="from_uid";

    public ApiUserWallettotal(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsHashMap = paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        return retrofit.create(ApiServices.class).getWalletToal(mParamsHashMap);

    }
}
