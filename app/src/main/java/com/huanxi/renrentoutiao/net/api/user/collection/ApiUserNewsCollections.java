package com.huanxi.renrentoutiao.net.api.user.collection;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.browerRecord.ResUserNewsBrowerRecord;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/3.
 */

public class ApiUserNewsCollections extends BaseApi<ResUserNewsBrowerRecord> {

    private final HashMap<String, String> mParamsMap;

    public static String FROM_UID="from_uid";
    public static String PAGE_SIZE="pageSize";
    public static String PAGE_NUM="pageNum";
    private static String TYPE="type";

    public ApiUserNewsCollections(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
        mParamsMap.put(TYPE,"1");
        mParamsMap.put(PAGE_SIZE,"20");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).requestMineCollection(mParamsMap);
    }

}
