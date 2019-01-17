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
 * 这里是金币明细的列表；
 */

public class ApiUserGoldCoinDetailList extends BaseApi<ResGoldDetailList> {

    private  final HashMap<String, String> mParamsMap;


    public static String FROM_UID="from_uid";
    public static String PAGE_SIZE="pageSize";
    public static String PAGE_NUM="pageNum";



    public ApiUserGoldCoinDetailList(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
        mParamsMap.put(PAGE_SIZE,"20");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getGoldCoinDetailList(mParamsMap);
    }
}
