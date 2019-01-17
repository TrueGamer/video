package com.huanxi.renrentoutiao.net.api.user.task;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.ui.adapter.bean.TaskTitleBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/6.
 */

public class ApiUserTaskList extends BaseApi<List<TaskTitleBean>> {

    HashMap<String,String> mParamsMap = new HashMap<String,String>();

    public static final String from_uid="from_uid";

    public ApiUserTaskList(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        this.mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getAllTask(mParamsMap);
    }
}
