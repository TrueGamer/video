package com.huanxi.renrentoutiao.net.api.share;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.api.BaseParamsApi;
import com.huanxi.renrentoutiao.net.bean.ResInviteFriendContent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/4/12.
 */

public class ApiInviteFriendContent extends BaseParamsApi<ResInviteFriendContent> {

    public static final String TYPE="to";

    public static final String WECHAT_FRIEND="1";
    public static final String WECHAT_FRIEND_COMMENT="2";
    public static final String QQ="3";

    public static final String INVITE_FRIEND_CONTENT="4";


    public ApiInviteFriendContent(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, HashMap<String, String> paramsMap) {
        super(listener, rxAppCompatActivity, paramsMap);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getInvietFriendContent(mParamsMap);
    }
}
