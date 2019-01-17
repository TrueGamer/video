package com.huanxi.renrentoutiao.net.api;

import android.widget.TextView;

import com.huanxi.renrentoutiao.net.ApiServices;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class ApiSMSSend extends BaseApi<ResEmpty> {

    public HashMap<String,String> mParamsMap;

    public static final String TYPE="type";
    public static final String PHONE_NUMBER="mobile";

    public static final String login_code="2";
    public static final String REGIEST_CODE ="1";
    public static final String BIND_PHONE_CODE = "3";

    public ApiSMSSend(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity,HashMap<String,String> paramsMap) {
        super(listener, rxAppCompatActivity);
        this.mParamsMap=paramsMap;
        setShowProgress(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(ApiServices.class).getValidPhoneNumber(mParamsMap);
    }

    /**
     * 这里是是一个默认的发送短信的方法
     * @param rxAppCompatActivity
     * @param view
     * @param phoneNumber
     * @param type
     */
    public static void defaultSend(final BaseActivity rxAppCompatActivity, final TextView view, String phoneNumber, String type){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(TYPE,type);
        paramsMap.put(PHONE_NUMBER,phoneNumber);

        ApiSMSSend apiSMSSend = new ApiSMSSend(new HttpOnNextListener<ResEmpty>() {

            @Override
            public void onNext(ResEmpty resEmpty) {
                rxAppCompatActivity.toast("发送成功！");
                UIUtils.countDowm(rxAppCompatActivity,60,view,"获取");
            }
        },rxAppCompatActivity,paramsMap);

        HttpManager.getInstance().doHttpDeal(apiSMSSend);
    }
}
