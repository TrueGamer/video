package com.huanxi.renrentoutiao.net.api.user;

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

public class ApiAlterUserInfo extends BaseApi<ResEmpty> {

    private HashMap<String,String> mParamsMap=new HashMap<>();

    public static final String USERNAME="nickname";
    public static final String SEX="sex";
    public static final String BITHDAY="birthday";
    public static final String PROVICE="province";
    public static final String CITY="city";
    public static final String COUNTRY="country";
    public static final String PHONE="phone";
    public static final String FROM_UID="from_uid";
    public static final String EAMIL="email";


    public ApiAlterUserInfo(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap)  {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).alterUserInfo(mParamsMap);
    }
}
