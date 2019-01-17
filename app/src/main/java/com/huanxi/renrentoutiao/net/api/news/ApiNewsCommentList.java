package com.huanxi.renrentoutiao.net.api.news;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.comment.ResNewsCommentList;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/2/2.
 */

public class ApiNewsCommentList extends BaseApi<ResNewsCommentList> {

    public static final String ISSUE_ID="urlmd5";
    public static final String PAGE_NUMBER="pageNum";
    private final HashMap<String, String> mParamsMap;


    public ApiNewsCommentList(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        mParamsMap = paramsMap;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getCommentList(mParamsMap);
    }
}
