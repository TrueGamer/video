package com.huanxi.renrentoutiao.net.api.news;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.api.BaseParamsApi;
import com.huanxi.renrentoutiao.net.bean.news.ResNewsAndVideoBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/4/11.
 */

public class ApiNewsAndVideoList extends BaseParamsApi<ResNewsAndVideoBean> {


    public static final String TAB_TYPE="type";

    public ApiNewsAndVideoList(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String, String> paramsMap) {
        super(listener, rxAppCompatActivity, paramsMap);
        mParamsMap.put(PAGE_SIZE,"20");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getNewsAndVideo(mParamsMap);
    }
}
