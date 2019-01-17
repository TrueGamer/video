package com.huanxi.renrentoutiao.net.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

/**
 * Created by Dinosa on 2018/3/14.
 * 这里是封装了一些公共参数的Api
 */

public abstract class BaseParamsApi<T> extends BaseApi<T> {


    protected HashMap<String,String> mParamsMap=new HashMap<>();

    public static final String FROM_UID="from_uid";
    public static String PAGE_SIZE="pageSize";
    public static String PAGE_NUM="pageNum";

    public BaseParamsApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

}
