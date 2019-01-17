package com.huanxi.renrentoutiao.net.api;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.user.ResGetRedPacketBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/6.
 *
 * 这里我们要做的一个操作就是获取拆红包的倒计时；
 */

public class ApiGetRedPacket extends BaseApi<ResGetRedPacketBean> {

    HashMap<String,String> mParamsMap = new HashMap<String,String>();

    public static final String FROM_UID="from_uid";

    public ApiGetRedPacket(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        this.mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getRedPacket(mParamsMap);
    }
}
