package com.huanxi.renrentoutiao.net.api.news;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.news.ResTabBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/25.
 */

public class ApiHomeNews extends BaseApi<ResTabBean> {

    private final HashMap<String, String> mParamsMap;
    public String type;

    public static final String FROM_UID="from_uid";
    public static String PAGE_SIZE="pageSize";
    public static String PAGE_NUM="pageNum";

    public ApiHomeNews(  RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap
            ,HttpOnNextListener listener) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
        setMothed(mParamsMap.toString());
        setCookieNoNetWorkTime(60*60*24*7);
        setCookieNetWorkTime(0);
        setCache(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getHomeNews(mParamsMap);
    }

}
