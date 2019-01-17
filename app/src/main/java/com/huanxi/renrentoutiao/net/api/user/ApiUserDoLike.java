package com.huanxi.renrentoutiao.net.api.user;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.api.BaseResultEntity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/22.
 * 这里是做点赞的逻辑
 */

public class ApiUserDoLike extends BaseApi<ResEmpty> {

    public static final String FROM_UID="from_uid";    //这里是用户的id；
    public static final String COMMENT_ID="comment_id";//评论的id:

    public HashMap<String,String> mParamsMap=new HashMap<>();

    public ApiUserDoLike(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap=paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).requestDoLike(mParamsMap);
    }

    String mMsg="";

    @Override
    public ResEmpty call(BaseResultEntity<ResEmpty> httpResult) {
        ResEmpty call = super.call(httpResult);
        mMsg=httpResult.getMsg();
        return call;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }
}
