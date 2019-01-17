package com.huanxi.renrentoutiao.net.api.user.task;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.money.ResGoldDetailList;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/5.
 * 这里是余额明细的列表；
 */

public class ApiUserMoneyDetailList extends BaseApi<ResGoldDetailList> {

    public static String FROM_UID="from_uid";
    public static String PAGE_SIZE="pageSize";
    public static String PAGE_NUM="pageNum";
    private final HashMap<String, String> mParamsMap;


    public ApiUserMoneyDetailList(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
        mParamsMap.put(PAGE_SIZE,"20");
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getMoneyDetailList(mParamsMap);
    }
}
