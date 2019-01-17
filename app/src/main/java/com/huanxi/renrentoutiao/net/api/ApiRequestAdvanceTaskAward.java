package com.huanxi.renrentoutiao.net.api;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.news.ResAward;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/4/13.
 * 高佣任务的接口；
 */

public class ApiRequestAdvanceTaskAward extends BaseParamsApi<ResAward> {

    public static final String TASK_ID="tid";

    public ApiRequestAdvanceTaskAward(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String, String> paramsMap) {
        super(listener, rxAppCompatActivity, paramsMap);
        setShowProgress(true);
    }

    //高佣任务的接口；
    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getAdvanceTaskAward(mParamsMap);
    }
}
