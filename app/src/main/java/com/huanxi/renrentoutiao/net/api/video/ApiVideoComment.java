package com.huanxi.renrentoutiao.net.api.video;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.video.ResVideoComment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.api.BaseResultEntity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class ApiVideoComment extends BaseApi<List<ResVideoComment.DataBean>> {

    HashMap<String,String> paramsMap = new HashMap<String,String>();

    public ApiVideoComment(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        this.paramsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getVedioComment(paramsMap);
    }

    @Override
    public List<ResVideoComment.DataBean> call(BaseResultEntity<List<ResVideoComment.DataBean>> httpResult) {
        return httpResult.getData();
    }
}
