package com.huanxi.renrentoutiao.net.api.user.browerRecord;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/8.
 */

public class ApiClearRecord extends BaseApi<ResEmpty> {

    public static final String TYPE="type";
    public static final String FROM_UID="from_uid";

    public static final String NEWS="1";
    public static final String VIDEO="2";


    public HashMap<String,String> mParamsMap = new HashMap<String,String>();

    public ApiClearRecord(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).clearBrowerRecord(mParamsMap);
    }
}
