package com.huanxi.renrentoutiao.net.api.user.userInfo;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/6.
 * 这里绑定手机号是一个单独的接口
 */

public class ApiBindPhoneNumber extends BaseApi<String> {

    HashMap<String,String> mParamsMap = new HashMap<String,String>();
    public  static final String PHONE_NUMBER="mobile";
    public  static final String PHONE_CODE="code";

    public static final String WECHAT_UNIONID="unionid";
    public static final String WECHAT_OPENID="openid";
    public static final String WECHAT_NICKNAME="nickname";
    public static final String WECHAT_SEX="sex";
    public static final String WECHAT_PROVINCE="province";
    public static final String WECHAT_CITY="city";
    public static final String WECHAT_COUNTRY="country";
    public static final String WECHAT_HEADIMGURL="headimgurl";

    public ApiBindPhoneNumber(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).bindPhoneNumber(mParamsMap);
    }
}
