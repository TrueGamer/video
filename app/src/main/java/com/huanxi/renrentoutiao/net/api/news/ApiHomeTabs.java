package com.huanxi.renrentoutiao.net.api.news;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.news.ResHomeTabs;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class ApiHomeTabs extends BaseApi<ResHomeTabs> {

    private final String mType;

    public static final String TYPE_NEWS="1";
    public static final String TYPE_VIDEO="2";


    public ApiHomeTabs(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, String type) {
        super(listener, rxAppCompatActivity);
        this.mType = type;
        setCache(true);
        setMothed("debit/public/api/news/top/get_new_men?type="+type);
       // setCookieNetWorkTime(60*60*24);
        setCookieNoNetWorkTime(60*60*24*7);
        setRetryCount(2);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        return retrofit.create(ApiServices.class).getHomeNewsType(mType);
    }
}
