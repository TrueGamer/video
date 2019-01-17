package com.huanxi.renrentoutiao.net.bean.money;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/5.
 */

public class ApiUserWithdrawals extends BaseApi<ResEmpty> {

    public static final String FROM_UID="from_uid";
    public static final String REAL_NAME="name";
    public static final String ALIPAY_ACCOUNT="account";
    public static final String MONEY="money";
    public static final String PayType = "payType";

    HashMap<String,String> mParamsMap=new HashMap<>();

    public ApiUserWithdrawals(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).requestWithdrawalsMoney(mParamsMap);
    }
}
