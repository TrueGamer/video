package com.huanxi.renrentoutiao.net.api.share;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.api.BaseParamsApi;
import com.huanxi.renrentoutiao.net.bean.ResRequestShare;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/4/12.
 */

public class ApiShareNewsAndVideoContent extends BaseParamsApi<ResRequestShare> {


    public static final String SHARE_TYPE="to";


    public static final String WECHAT_FRIEND="1";
    public static final String WECHAT_FRIEND_COMMENT="2";
    public static final String QQ="3";
    public static final String SINA = "4";

    public static final String TYPE="type"; //分享的类型的操作；

    public static final String NEWS_TYPE="1"; //文章
    public static final String VIDEO_TYPE="2"; //视频；

    public static final String URLMD5="urlmd5";



    public ApiShareNewsAndVideoContent(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String, String> paramsMap) {
        super(listener, rxAppCompatActivity, paramsMap);
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        return retrofit.create(ApiServices.class).getShareArticleAndVideoContent(mParamsMap);

    }
}
